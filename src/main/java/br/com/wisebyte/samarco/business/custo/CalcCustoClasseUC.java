package br.com.wisebyte.samarco.business.custo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO.CustoClasseEstadoDTO;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO.CustoClasseTotalDTO;
import br.com.wisebyte.samarco.dto.custo.DistribuicaoCargaResultDTO;
import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.model.demanda.TipoHorario;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL64;

/**
 * Calcula o Custo por Classe de Custo.
 * Baseado na Sheet 14 da planilha - "Distrib de Classe de Custo".
 *
 * Classes:
 * - 50610002: Consumo de Energia = Energia Comprada × PMIX
 * - 50610003: Uso da Rede = Demanda Ponta × Tarifa Ponta + Demanda FP × Tarifa FP
 * - 50610006: Encargos = Consumo × Encargo - Desconto Auto-Produção
 */
@ApplicationScoped
public class CalcCustoClasseUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalcDistribuicaoCargaUC calcDistribuicaoCargaUC;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    TarifaDistribuidoraRepository tarifaDistribuidoraRepository;

    @Inject
    TarifaFornecedorRepository tarifaFornecedorRepository;

    @Inject
    DemandaRepository demandaRepository;

    public CustoClasseResultDTO calcular(@NotNull Long revisaoId) {
        Revisao revisao = revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("CustoClasse", "Revisao", "Revisao nao encontrada"));

        // 1. Calcular distribuição de carga (consumo, geração por estado)
        DistribuicaoCargaResultDTO distribuicaoCarga = calcDistribuicaoCargaUC.calcular(revisaoId);

        // 2. Calcular PMIX (preço médio ponderado dos fornecedores)
        BigDecimal pmix = calcularPMIX(revisaoId);

        // 3. Buscar tarifas por distribuidora
        var tarifaPlanejamento = tarifaPlanejamentoRepository.findByRevisaoId(revisaoId).orElse(null);
        List<TarifaDistribuidora> tarifasDistribuidora = tarifaPlanejamento != null
                ? tarifaDistribuidoraRepository.findByTarifaPlanejamento(tarifaPlanejamento)
                : List.of();

        // 4. Buscar demandas
        List<Demanda> demandas = demandaRepository.findByRevisaoId(revisaoId);

        // 5. Calcular custos por estado
        int anoBase = revisao.getPlanejamento().getAno();
        CustoClasseEstadoDTO custoMG = calcularCustoEstado(
                Estado.MG, anoBase, distribuicaoCarga.getMinasGerais(),
                tarifasDistribuidora, demandas, pmix);
        CustoClasseEstadoDTO custoES = calcularCustoEstado(
                Estado.ES, anoBase, distribuicaoCarga.getEspiritoSanto(),
                tarifasDistribuidora, demandas, pmix);

        // 6. Calcular totais
        CustoClasseTotalDTO totalSamarco = CustoClasseTotalDTO.builder()
                .consumoEnergiaTotal(safe(custoMG.getConsumoEnergiaTotal()).add(safe(custoES.getConsumoEnergiaTotal())))
                .usoRedeTotal(safe(custoMG.getUsoRedeTotal()).add(safe(custoES.getUsoRedeTotal())))
                .encargosTotal(safe(custoMG.getEncargosTotal()).add(safe(custoES.getEncargosTotal())))
                .totalGeral(safe(custoMG.getTotalGeral()).add(safe(custoES.getTotalGeral())))
                .build();

        return CustoClasseResultDTO.builder()
                .revisaoId(revisaoId)
                .minasGerais(custoMG)
                .espiritoSanto(custoES)
                .totalSamarco(totalSamarco)
                .pmix(pmix)
                .build();
    }

    /**
     * Calcula PMIX = Soma(Preço × Montante) / Soma(Montantes)
     * Baseado na Sheet 1, célula I16.
     */
    private BigDecimal calcularPMIX(Long revisaoId) {
        var tarifaPlanejamento = tarifaPlanejamentoRepository.findByRevisaoId(revisaoId).orElse(null);
        if (tarifaPlanejamento == null) return ZERO;

        List<TarifaFornecedor> tarifasFornecedor = tarifaFornecedorRepository.findByTarifaPlanejamento(tarifaPlanejamento);
        if (tarifasFornecedor.isEmpty()) return ZERO;

        BigDecimal somaValores = ZERO;
        BigDecimal somaMontantes = ZERO;

        for (TarifaFornecedor tf : tarifasFornecedor) {
            // Preço = PreçoBase × (1 + IPCA Realizado + IPCA Projetado)
            BigDecimal precoBase = tf.getFornecedor().getPrecoBase();
            BigDecimal ipcaTotal = safe(tf.getIpcaRealizada()).add(safe(tf.getIpcaProjetado()));
            BigDecimal precoAtualizado = precoBase.multiply(BigDecimal.ONE.add(ipcaTotal), DECIMAL64);

            BigDecimal montante = safe(tf.getMontante());
            BigDecimal valorMontante = precoAtualizado.multiply(montante, DECIMAL64);

            somaValores = somaValores.add(valorMontante);
            somaMontantes = somaMontantes.add(montante);
        }

        if (somaMontantes.compareTo(ZERO) == 0) return ZERO;
        return somaValores.divide(somaMontantes, 6, RoundingMode.HALF_UP);
    }

    private CustoClasseEstadoDTO calcularCustoEstado(
            Estado estado,
            int anoBase,
            DistribuicaoCargaResultDTO.CargaEstadoDTO cargaEstado,
            List<TarifaDistribuidora> tarifasDistribuidora,
            List<Demanda> demandas,
            BigDecimal pmix) {

        if (cargaEstado == null) {
            return CustoClasseEstadoDTO.builder().build();
        }

        BigDecimal[] consumoEnergia = new BigDecimal[12];
        BigDecimal[] usoRede = new BigDecimal[12];
        BigDecimal[] encargos = new BigDecimal[12];
        BigDecimal[] descontoAutoProducao = new BigDecimal[12];
        BigDecimal[] total = new BigDecimal[12];

        // Necessidade de compra por mês
        BigDecimal[] necessidadeCompra = new BigDecimal[] {
                safe(cargaEstado.getNecessidadeCompraJaneiro()),
                safe(cargaEstado.getNecessidadeCompraFevereiro()),
                safe(cargaEstado.getNecessidadeCompraMarco()),
                safe(cargaEstado.getNecessidadeCompraAbril()),
                safe(cargaEstado.getNecessidadeCompraMaio()),
                safe(cargaEstado.getNecessidadeCompraJunho()),
                safe(cargaEstado.getNecessidadeCompraJulho()),
                safe(cargaEstado.getNecessidadeCompraAgosto()),
                safe(cargaEstado.getNecessidadeCompraSetembro()),
                safe(cargaEstado.getNecessidadeCompraOutubro()),
                safe(cargaEstado.getNecessidadeCompraNovembro()),
                safe(cargaEstado.getNecessidadeCompraDezembro())
        };

        // Consumo total por mês (para encargos)
        BigDecimal[] consumoMensal = new BigDecimal[] {
                safe(cargaEstado.getConsumoJaneiro()),
                safe(cargaEstado.getConsumoFevereiro()),
                safe(cargaEstado.getConsumoMarco()),
                safe(cargaEstado.getConsumoAbril()),
                safe(cargaEstado.getConsumoMaio()),
                safe(cargaEstado.getConsumoJunho()),
                safe(cargaEstado.getConsumoJulho()),
                safe(cargaEstado.getConsumoAgosto()),
                safe(cargaEstado.getConsumoSetembro()),
                safe(cargaEstado.getConsumoOutubro()),
                safe(cargaEstado.getConsumoNovembro()),
                safe(cargaEstado.getConsumoDezembro())
        };

        // Geração por mês (para desconto auto-produção)
        BigDecimal[] geracaoMensal = new BigDecimal[] {
                safe(cargaEstado.getGeracaoJaneiro()),
                safe(cargaEstado.getGeracaoFevereiro()),
                safe(cargaEstado.getGeracaoMarco()),
                safe(cargaEstado.getGeracaoAbril()),
                safe(cargaEstado.getGeracaoMaio()),
                safe(cargaEstado.getGeracaoJunho()),
                safe(cargaEstado.getGeracaoJulho()),
                safe(cargaEstado.getGeracaoAgosto()),
                safe(cargaEstado.getGeracaoSetembro()),
                safe(cargaEstado.getGeracaoOutubro()),
                safe(cargaEstado.getGeracaoNovembro()),
                safe(cargaEstado.getGeracaoDezembro())
        };

        for (int mes = 0; mes < 12; mes++) {
            LocalDate dataMes = LocalDate.of(anoBase, mes + 1, 15);

            // 50610002 - Consumo de Energia = Necessidade Compra × PMIX
            consumoEnergia[mes] = necessidadeCompra[mes].multiply(pmix, DECIMAL64);

            // 50610003 - Uso da Rede = Demanda × Tarifa
            usoRede[mes] = calcularUsoRede(estado, dataMes, demandas, tarifasDistribuidora);

            // 50610006 - Encargos
            // Buscar tarifa de encargo e desconto auto-produção
            BigDecimal tarifaEncargo = buscarTarifaEncargo(estado, dataMes, tarifasDistribuidora);
            BigDecimal descontoAutoProducaoTarifa = buscarDescontoAutoProducao(estado, dataMes, tarifasDistribuidora);

            encargos[mes] = consumoMensal[mes].multiply(tarifaEncargo, DECIMAL64);
            descontoAutoProducao[mes] = geracaoMensal[mes].multiply(descontoAutoProducaoTarifa, DECIMAL64);

            // Encargo líquido = Encargo bruto - Desconto (não pode ser negativo)
            BigDecimal encargoLiquido = encargos[mes].subtract(descontoAutoProducao[mes]);
            if (encargoLiquido.compareTo(ZERO) < 0) {
                encargoLiquido = ZERO;
            }
            encargos[mes] = encargoLiquido;

            // Total do mês
            total[mes] = consumoEnergia[mes].add(usoRede[mes]).add(encargos[mes]);
        }

        return CustoClasseEstadoDTO.builder()
                .consumoEnergiaJaneiro(consumoEnergia[0])
                .consumoEnergiaFevereiro(consumoEnergia[1])
                .consumoEnergiaMarco(consumoEnergia[2])
                .consumoEnergiaAbril(consumoEnergia[3])
                .consumoEnergiaMaio(consumoEnergia[4])
                .consumoEnergiaJunho(consumoEnergia[5])
                .consumoEnergiaJulho(consumoEnergia[6])
                .consumoEnergiaAgosto(consumoEnergia[7])
                .consumoEnergiaSetembro(consumoEnergia[8])
                .consumoEnergiaOutubro(consumoEnergia[9])
                .consumoEnergiaNovembro(consumoEnergia[10])
                .consumoEnergiaDezembro(consumoEnergia[11])
                .consumoEnergiaTotal(somarArray(consumoEnergia))
                .usoRedeJaneiro(usoRede[0])
                .usoRedeFevereiro(usoRede[1])
                .usoRedeMarco(usoRede[2])
                .usoRedeAbril(usoRede[3])
                .usoRedeMaio(usoRede[4])
                .usoRedeJunho(usoRede[5])
                .usoRedeJulho(usoRede[6])
                .usoRedeAgosto(usoRede[7])
                .usoRedeSetembro(usoRede[8])
                .usoRedeOutubro(usoRede[9])
                .usoRedeNovembro(usoRede[10])
                .usoRedeDezembro(usoRede[11])
                .usoRedeTotal(somarArray(usoRede))
                .encargosJaneiro(encargos[0])
                .encargosFevereiro(encargos[1])
                .encargosMarco(encargos[2])
                .encargosAbril(encargos[3])
                .encargosMaio(encargos[4])
                .encargosJunho(encargos[5])
                .encargosJulho(encargos[6])
                .encargosAgosto(encargos[7])
                .encargosSetembro(encargos[8])
                .encargosOutubro(encargos[9])
                .encargosNovembro(encargos[10])
                .encargosDezembro(encargos[11])
                .encargosTotal(somarArray(encargos))
                .descontoAutoProducaoJaneiro(descontoAutoProducao[0])
                .descontoAutoProducaoFevereiro(descontoAutoProducao[1])
                .descontoAutoProducaoMarco(descontoAutoProducao[2])
                .descontoAutoProducaoAbril(descontoAutoProducao[3])
                .descontoAutoProducaoMaio(descontoAutoProducao[4])
                .descontoAutoProducaoJunho(descontoAutoProducao[5])
                .descontoAutoProducaoJulho(descontoAutoProducao[6])
                .descontoAutoProducaoAgosto(descontoAutoProducao[7])
                .descontoAutoProducaoSetembro(descontoAutoProducao[8])
                .descontoAutoProducaoOutubro(descontoAutoProducao[9])
                .descontoAutoProducaoNovembro(descontoAutoProducao[10])
                .descontoAutoProducaoDezembro(descontoAutoProducao[11])
                .descontoAutoProducaoTotal(somarArray(descontoAutoProducao))
                .totalJaneiro(total[0])
                .totalFevereiro(total[1])
                .totalMarco(total[2])
                .totalAbril(total[3])
                .totalMaio(total[4])
                .totalJunho(total[5])
                .totalJulho(total[6])
                .totalAgosto(total[7])
                .totalSetembro(total[8])
                .totalOutubro(total[9])
                .totalNovembro(total[10])
                .totalDezembro(total[11])
                .totalGeral(somarArray(total))
                .build();
    }

    /**
     * Calcula custo de uso da rede = (Demanda Ponta × Tarifa Ponta) + (Demanda FP × Tarifa FP)
     */
    private BigDecimal calcularUsoRede(Estado estado, LocalDate dataMes,
                                        List<Demanda> demandas, List<TarifaDistribuidora> tarifas) {
        int mesIndex = dataMes.getMonthValue() - 1;
        BigDecimal totalUsoRede = ZERO;

        // Buscar demandas do estado
        for (Demanda demanda : demandas) {
            if (demanda.getUnidade().getEstado() != estado) continue;

            BigDecimal valorDemanda = getValorMes(demanda, mesIndex);
            if (valorDemanda.compareTo(ZERO) == 0) continue;

            // Buscar tarifa da distribuidora da unidade
            TarifaDistribuidora tarifa = buscarTarifaVigente(
                    demanda.getUnidade().getDistribuidora().getId(), dataMes, tarifas);

            if (tarifa != null) {
                if (demanda.getTipoHorario() == TipoHorario.PONTA) {
                    totalUsoRede = totalUsoRede.add(valorDemanda.multiply(tarifa.getValorPonta(), DECIMAL64));
                } else {
                    totalUsoRede = totalUsoRede.add(valorDemanda.multiply(tarifa.getValorForaPonta(), DECIMAL64));
                }
            }
        }

        return totalUsoRede;
    }

    private BigDecimal buscarTarifaEncargo(Estado estado, LocalDate dataMes, List<TarifaDistribuidora> tarifas) {
        for (TarifaDistribuidora tarifa : tarifas) {
            if (tarifa.getDistribuidora().getEstado() == estado
                    && !dataMes.isBefore(tarifa.getPeriodoInicial())
                    && !dataMes.isAfter(tarifa.getPeriodoFinal())) {
                return safe(tarifa.getValorEncargos());
            }
        }
        return ZERO;
    }

    private BigDecimal buscarDescontoAutoProducao(Estado estado, LocalDate dataMes, List<TarifaDistribuidora> tarifas) {
        for (TarifaDistribuidora tarifa : tarifas) {
            if (tarifa.getDistribuidora().getEstado() == estado
                    && !dataMes.isBefore(tarifa.getPeriodoInicial())
                    && !dataMes.isAfter(tarifa.getPeriodoFinal())) {
                // Desconto = Encargo Total - Encargo Auto Produtor
                BigDecimal encargoTotal = safe(tarifa.getValorEncargos());
                BigDecimal encargoAutoProdutor = safe(tarifa.getValorEncargosAutoProducao());
                return encargoTotal.subtract(encargoAutoProdutor);
            }
        }
        return ZERO;
    }

    private TarifaDistribuidora buscarTarifaVigente(Long distribuidoraId, LocalDate dataMes, List<TarifaDistribuidora> tarifas) {
        for (TarifaDistribuidora tarifa : tarifas) {
            if (tarifa.getDistribuidora().getId().equals(distribuidoraId)
                    && !dataMes.isBefore(tarifa.getPeriodoInicial())
                    && !dataMes.isAfter(tarifa.getPeriodoFinal())) {
                return tarifa;
            }
        }
        return null;
    }

    private BigDecimal getValorMes(Demanda demanda, int mesIndex) {
        return switch (mesIndex) {
            case 0 -> safe(demanda.getValorJaneiro());
            case 1 -> safe(demanda.getValorFevereiro());
            case 2 -> safe(demanda.getValorMarco());
            case 3 -> safe(demanda.getValorAbril());
            case 4 -> safe(demanda.getValorMaio());
            case 5 -> safe(demanda.getValorJunho());
            case 6 -> safe(demanda.getValorJulho());
            case 7 -> safe(demanda.getValorAgosto());
            case 8 -> safe(demanda.getValorSetembro());
            case 9 -> safe(demanda.getValorOutubro());
            case 10 -> safe(demanda.getValorNovembro());
            case 11 -> safe(demanda.getValorDezembro());
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
