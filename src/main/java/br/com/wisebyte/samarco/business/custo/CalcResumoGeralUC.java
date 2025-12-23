package br.com.wisebyte.samarco.business.custo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO;
import br.com.wisebyte.samarco.dto.custo.DistribuicaoCargaResultDTO;
import br.com.wisebyte.samarco.dto.custo.ResumoGeralResultDTO;
import br.com.wisebyte.samarco.dto.custo.ResumoGeralResultDTO.CustosMensaisDTO;
import br.com.wisebyte.samarco.model.encargo.EncargosSetoriais;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.encargo.EncargosSetoriaisRepository;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.MathContext.DECIMAL64;

/**
 * Calcula o Resumo Geral de Custos.
 * Baseado na Sheet 16 da planilha - "Resumo GERAL".
 *
 * Consolida todos os custos de energia elétrica.
 */
@ApplicationScoped
public class CalcResumoGeralUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalcCustoClasseUC calcCustoClasseUC;

    @Inject
    CalcDistribuicaoCargaUC calcDistribuicaoCargaUC;

    @Inject
    EncargosSetoriaisRepository encargosRepository;

    @Inject
    PlanejamentoProducaoRepository producaoRepository;

    @Inject
    ProducaoConfigRepository producaoConfigRepository;

    public ResumoGeralResultDTO calcular(@NotNull Long revisaoId) {
        var revisao = revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("ResumoGeral", "Revisao", "Revisao nao encontrada"));

        // 1. Buscar custos por classe
        CustoClasseResultDTO custoClasse = calcCustoClasseUC.calcular(revisaoId);

        // 2. Buscar distribuição de carga (para calcular encargos ESS/EER)
        DistribuicaoCargaResultDTO distribuicaoCarga = calcDistribuicaoCargaUC.calcular(revisaoId);

        // 3. Buscar encargos setoriais (ESS/EER)
        EncargosSetoriais encargos = encargosRepository.findByRevisaoId(revisaoId).orElse(null);

        // 4. Calcular custos de ESS/EER
        // ESS/EER incide sobre energia comprada (Necessidade de Compra)
        BigDecimal[] necessidadeCompra = new BigDecimal[] {
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraJaneiro()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraFevereiro()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraMarco()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraAbril()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraMaio()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraJunho()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraJulho()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraAgosto()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraSetembro()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraOutubro()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraNovembro()),
                safe(distribuicaoCarga.getTotalSamarco().getNecessidadeCompraDezembro())
        };

        CustosMensaisDTO eerErcap = calcularEncargosEerErcap(encargos, necessidadeCompra);
        CustosMensaisDTO ess = calcularEncargosEss(encargos, necessidadeCompra);

        // 5. Montar custos por classe (consolidando MG + ES)
        CustosMensaisDTO consumoEnergia = montarCustosMensais(custoClasse, "consumoEnergia");
        CustosMensaisDTO usoRede = montarCustosMensais(custoClasse, "usoRede");
        CustosMensaisDTO encargosCusto = montarCustosMensais(custoClasse, "encargos");

        // 6. Calcular totais variáveis
        CustosMensaisDTO totalVariavel = somarCustosMensais(consumoEnergia, usoRede, encargosCusto, eerErcap, ess);

        // 7. Calcular produção total
        BigDecimal producaoTotal = calcularProducaoTotal(revisaoId);

        // 8. Calcular custo específico
        BigDecimal custoEspecifico = ZERO;
        if (producaoTotal.compareTo(ZERO) > 0) {
            custoEspecifico = totalVariavel.getTotal().divide(producaoTotal, 4, RoundingMode.HALF_UP);
        }

        return ResumoGeralResultDTO.builder()
                .revisaoId(revisaoId)
                .consumoEnergia(consumoEnergia)
                .usoRede(usoRede)
                .encargos(encargosCusto)
                .eerErcap(eerErcap)
                .ess(ess)
                .totalVariavel(totalVariavel)
                .totalGeral(totalVariavel) // Por enquanto, sem custos fixos
                .producaoTotalTms(producaoTotal)
                .custoEspecificoTotal(custoEspecifico)
                .totalConsumoEnergiaAnual(consumoEnergia.getTotal())
                .totalUsoRedeAnual(usoRede.getTotal())
                .totalEncargosAnual(encargosCusto.getTotal())
                .totalEerErcapAnual(eerErcap.getTotal())
                .totalEssAnual(ess.getTotal())
                .totalVariavelAnual(totalVariavel.getTotal())
                .totalGeralAnual(totalVariavel.getTotal())
                .build();
    }

    private CustosMensaisDTO calcularEncargosEerErcap(EncargosSetoriais encargos, BigDecimal[] necessidadeCompra) {
        if (encargos == null) {
            return CustosMensaisDTO.builder().total(ZERO).build();
        }

        // EER + ERCAP por mês × Necessidade de Compra
        BigDecimal[] custos = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            BigDecimal eerMes = getEerMes(encargos, i);
            BigDecimal ercapMes = getErcapMes(encargos, i);
            BigDecimal tarifaTotal = eerMes.add(ercapMes);
            custos[i] = necessidadeCompra[i].multiply(tarifaTotal, DECIMAL64);
        }

        return CustosMensaisDTO.builder()
                .janeiro(custos[0])
                .fevereiro(custos[1])
                .marco(custos[2])
                .abril(custos[3])
                .maio(custos[4])
                .junho(custos[5])
                .julho(custos[6])
                .agosto(custos[7])
                .setembro(custos[8])
                .outubro(custos[9])
                .novembro(custos[10])
                .dezembro(custos[11])
                .total(somarArray(custos))
                .build();
    }

    private CustosMensaisDTO calcularEncargosEss(EncargosSetoriais encargos, BigDecimal[] necessidadeCompra) {
        if (encargos == null) {
            return CustosMensaisDTO.builder().total(ZERO).build();
        }

        BigDecimal[] custos = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            BigDecimal essMes = getEssMes(encargos, i);
            custos[i] = necessidadeCompra[i].multiply(essMes, DECIMAL64);
        }

        return CustosMensaisDTO.builder()
                .janeiro(custos[0])
                .fevereiro(custos[1])
                .marco(custos[2])
                .abril(custos[3])
                .maio(custos[4])
                .junho(custos[5])
                .julho(custos[6])
                .agosto(custos[7])
                .setembro(custos[8])
                .outubro(custos[9])
                .novembro(custos[10])
                .dezembro(custos[11])
                .total(somarArray(custos))
                .build();
    }

    private CustosMensaisDTO montarCustosMensais(CustoClasseResultDTO custoClasse, String tipo) {
        var mg = custoClasse.getMinasGerais();
        var es = custoClasse.getEspiritoSanto();

        BigDecimal[] custos = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            BigDecimal valorMG = getValorMes(mg, tipo, i);
            BigDecimal valorES = getValorMes(es, tipo, i);
            custos[i] = valorMG.add(valorES);
        }

        return CustosMensaisDTO.builder()
                .janeiro(custos[0])
                .fevereiro(custos[1])
                .marco(custos[2])
                .abril(custos[3])
                .maio(custos[4])
                .junho(custos[5])
                .julho(custos[6])
                .agosto(custos[7])
                .setembro(custos[8])
                .outubro(custos[9])
                .novembro(custos[10])
                .dezembro(custos[11])
                .total(somarArray(custos))
                .build();
    }

    private BigDecimal getValorMes(CustoClasseResultDTO.CustoClasseEstadoDTO estado, String tipo, int mes) {
        if (estado == null) return ZERO;

        return switch (tipo) {
            case "consumoEnergia" -> switch (mes) {
                case 0 -> safe(estado.getConsumoEnergiaJaneiro());
                case 1 -> safe(estado.getConsumoEnergiaFevereiro());
                case 2 -> safe(estado.getConsumoEnergiaMarco());
                case 3 -> safe(estado.getConsumoEnergiaAbril());
                case 4 -> safe(estado.getConsumoEnergiaMaio());
                case 5 -> safe(estado.getConsumoEnergiaJunho());
                case 6 -> safe(estado.getConsumoEnergiaJulho());
                case 7 -> safe(estado.getConsumoEnergiaAgosto());
                case 8 -> safe(estado.getConsumoEnergiaSetembro());
                case 9 -> safe(estado.getConsumoEnergiaOutubro());
                case 10 -> safe(estado.getConsumoEnergiaNovembro());
                case 11 -> safe(estado.getConsumoEnergiaDezembro());
                default -> ZERO;
            };
            case "usoRede" -> switch (mes) {
                case 0 -> safe(estado.getUsoRedeJaneiro());
                case 1 -> safe(estado.getUsoRedeFevereiro());
                case 2 -> safe(estado.getUsoRedeMarco());
                case 3 -> safe(estado.getUsoRedeAbril());
                case 4 -> safe(estado.getUsoRedeMaio());
                case 5 -> safe(estado.getUsoRedeJunho());
                case 6 -> safe(estado.getUsoRedeJulho());
                case 7 -> safe(estado.getUsoRedeAgosto());
                case 8 -> safe(estado.getUsoRedeSetembro());
                case 9 -> safe(estado.getUsoRedeOutubro());
                case 10 -> safe(estado.getUsoRedeNovembro());
                case 11 -> safe(estado.getUsoRedeDezembro());
                default -> ZERO;
            };
            case "encargos" -> switch (mes) {
                case 0 -> safe(estado.getEncargosJaneiro());
                case 1 -> safe(estado.getEncargosFevereiro());
                case 2 -> safe(estado.getEncargosMarco());
                case 3 -> safe(estado.getEncargosAbril());
                case 4 -> safe(estado.getEncargosMaio());
                case 5 -> safe(estado.getEncargosJunho());
                case 6 -> safe(estado.getEncargosJulho());
                case 7 -> safe(estado.getEncargosAgosto());
                case 8 -> safe(estado.getEncargosSetembro());
                case 9 -> safe(estado.getEncargosOutubro());
                case 10 -> safe(estado.getEncargosNovembro());
                case 11 -> safe(estado.getEncargosDezembro());
                default -> ZERO;
            };
            default -> ZERO;
        };
    }

    private CustosMensaisDTO somarCustosMensais(CustosMensaisDTO... custos) {
        BigDecimal[] totais = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            totais[i] = ZERO;
        }

        for (CustosMensaisDTO c : custos) {
            if (c == null) continue;
            totais[0] = totais[0].add(safe(c.getJaneiro()));
            totais[1] = totais[1].add(safe(c.getFevereiro()));
            totais[2] = totais[2].add(safe(c.getMarco()));
            totais[3] = totais[3].add(safe(c.getAbril()));
            totais[4] = totais[4].add(safe(c.getMaio()));
            totais[5] = totais[5].add(safe(c.getJunho()));
            totais[6] = totais[6].add(safe(c.getJulho()));
            totais[7] = totais[7].add(safe(c.getAgosto()));
            totais[8] = totais[8].add(safe(c.getSetembro()));
            totais[9] = totais[9].add(safe(c.getOutubro()));
            totais[10] = totais[10].add(safe(c.getNovembro()));
            totais[11] = totais[11].add(safe(c.getDezembro()));
        }

        return CustosMensaisDTO.builder()
                .janeiro(totais[0])
                .fevereiro(totais[1])
                .marco(totais[2])
                .abril(totais[3])
                .maio(totais[4])
                .junho(totais[5])
                .julho(totais[6])
                .agosto(totais[7])
                .setembro(totais[8])
                .outubro(totais[9])
                .novembro(totais[10])
                .dezembro(totais[11])
                .total(somarArray(totais))
                .build();
    }

    private BigDecimal calcularProducaoTotal(Long revisaoId) {
        var revisao = revisaoRepository.findById(revisaoId).orElse(null);
        if (revisao == null) return ZERO;

        var config = producaoConfigRepository.findByRevisaoId(revisaoId).orElse(null);
        BigDecimal multiplicador = config != null ? valueOf(config.getMultiplicador()) : BigDecimal.ONE;

        List<PlanejamentoProducao> producoes = producaoRepository.findByRevisao(revisao);

        BigDecimal total = ZERO;
        for (PlanejamentoProducao p : producoes) {
            // Somar todos os meses × multiplicador
            total = total.add(safe(p.getValorJaneiro()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorFevereiro()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorMarco()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorAbril()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorMaio()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorJunho()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorJulho()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorAgosto()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorSetembro()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorOutubro()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorNovembro()).multiply(multiplicador, DECIMAL64));
            total = total.add(safe(p.getValorDezembro()).multiply(multiplicador, DECIMAL64));
        }

        return total;
    }

    private BigDecimal getEerMes(EncargosSetoriais e, int mes) {
        return switch (mes) {
            case 0 -> safe(e.getEerJaneiro());
            case 1 -> safe(e.getEerFevereiro());
            case 2 -> safe(e.getEerMarco());
            case 3 -> safe(e.getEerAbril());
            case 4 -> safe(e.getEerMaio());
            case 5 -> safe(e.getEerJunho());
            case 6 -> safe(e.getEerJulho());
            case 7 -> safe(e.getEerAgosto());
            case 8 -> safe(e.getEerSetembro());
            case 9 -> safe(e.getEerOutubro());
            case 10 -> safe(e.getEerNovembro());
            case 11 -> safe(e.getEerDezembro());
            default -> ZERO;
        };
    }

    private BigDecimal getErcapMes(EncargosSetoriais e, int mes) {
        return switch (mes) {
            case 0 -> safe(e.getErcapJaneiro());
            case 1 -> safe(e.getErcapFevereiro());
            case 2 -> safe(e.getErcapMarco());
            case 3 -> safe(e.getErcapAbril());
            case 4 -> safe(e.getErcapMaio());
            case 5 -> safe(e.getErcapJunho());
            case 6 -> safe(e.getErcapJulho());
            case 7 -> safe(e.getErcapAgosto());
            case 8 -> safe(e.getErcapSetembro());
            case 9 -> safe(e.getErcapOutubro());
            case 10 -> safe(e.getErcapNovembro());
            case 11 -> safe(e.getErcapDezembro());
            default -> ZERO;
        };
    }

    private BigDecimal getEssMes(EncargosSetoriais e, int mes) {
        return switch (mes) {
            case 0 -> safe(e.getEssJaneiro());
            case 1 -> safe(e.getEssFevereiro());
            case 2 -> safe(e.getEssMarco());
            case 3 -> safe(e.getEssAbril());
            case 4 -> safe(e.getEssMaio());
            case 5 -> safe(e.getEssJunho());
            case 6 -> safe(e.getEssJulho());
            case 7 -> safe(e.getEssAgosto());
            case 8 -> safe(e.getEssSetembro());
            case 9 -> safe(e.getEssOutubro());
            case 10 -> safe(e.getEssNovembro());
            case 11 -> safe(e.getEssDezembro());
            default -> ZERO;
        };
    }

    private BigDecimal somarArray(BigDecimal[] arr) {
        BigDecimal total = ZERO;
        for (BigDecimal v : arr) {
            total = total.add(safe(v));
        }
        return total;
    }

    private BigDecimal safe(BigDecimal valor) {
        return valor != null ? valor : ZERO;
    }
}
