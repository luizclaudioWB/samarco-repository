package br.com.wisebyte.samarco.business.custo;

import br.com.wisebyte.samarco.business.consumo.CalcConsumoAreaUC;
import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.dto.custo.DistribuicaoCargaResultDTO;
import br.com.wisebyte.samarco.dto.custo.DistribuicaoCargaResultDTO.CargaEstadoDTO;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.geracao.PlanejamentoGeracao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL64;

/**
 * Calcula a Distribuição de Carga por Estado.
 * Baseado na Sheet 13 da planilha - "Distribuição de Carga".
 *
 * Fórmulas:
 * - Perdas = Consumo × 3%
 * - Necessidade Total = Consumo + Perdas
 * - Necessidade de Compra = MAX(Necessidade Total - Geração, 0)
 */
@ApplicationScoped
public class CalcDistribuicaoCargaUC {

    private static final BigDecimal PERCENTUAL_PERDA = new BigDecimal("0.03"); // 3%
    private static final BigDecimal MIL = new BigDecimal("1000");

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalcConsumoAreaUC calcConsumoAreaUC;

    @Inject
    PlanejamentoGeracaoRepository geracaoRepository;

    @Inject
    AreaRepository areaRepository;

    public DistribuicaoCargaResultDTO calcular(@NotNull Long revisaoId) {
        revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("DistribuicaoCarga", "Revisao", "Revisao nao encontrada"));

        // 1. Buscar consumo por área (kWh) e converter para MWh
        var consumoAreas = calcConsumoAreaUC.calcConsumoArea(revisaoId).getResults();

        // 2. Separar consumo por estado
        BigDecimal[] consumoMG = new BigDecimal[12];
        BigDecimal[] consumoES = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            consumoMG[i] = ZERO;
            consumoES[i] = ZERO;
        }

        for (ConsumoAreaResultDTO consumo : consumoAreas) {
            var area = areaRepository.findById(consumo.getArea().getId()).orElse(null);
            if (area == null) continue;

            Estado estado = area.getUnidade().getEstado();
            BigDecimal[] destino = (estado == Estado.MG) ? consumoMG : consumoES;

            // Converter kWh para MWh (dividir por 1000)
            destino[0] = destino[0].add(dividirPorMil(consumo.getConsumoJaneiro()));
            destino[1] = destino[1].add(dividirPorMil(consumo.getConsumoFevereiro()));
            destino[2] = destino[2].add(dividirPorMil(consumo.getConsumoMarco()));
            destino[3] = destino[3].add(dividirPorMil(consumo.getConsumoAbril()));
            destino[4] = destino[4].add(dividirPorMil(consumo.getConsumoMaio()));
            destino[5] = destino[5].add(dividirPorMil(consumo.getConsumoJunho()));
            destino[6] = destino[6].add(dividirPorMil(consumo.getConsumoJulho()));
            destino[7] = destino[7].add(dividirPorMil(consumo.getConsumoAgosto()));
            destino[8] = destino[8].add(dividirPorMil(consumo.getConsumoSetembro()));
            destino[9] = destino[9].add(dividirPorMil(consumo.getConsumoOutubro()));
            destino[10] = destino[10].add(dividirPorMil(consumo.getConsumoNovembro()));
            destino[11] = destino[11].add(dividirPorMil(consumo.getConsumoDezembro()));
        }

        // 3. Buscar geração por usina e separar por estado
        List<PlanejamentoGeracao> geracoes = geracaoRepository.findByRevisaoId(revisaoId);
        BigDecimal[] geracaoMG = new BigDecimal[12];
        BigDecimal[] geracaoES = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            geracaoMG[i] = ZERO;
            geracaoES[i] = ZERO;
        }

        for (PlanejamentoGeracao geracao : geracoes) {
            Estado estado = geracao.getUnidade().getEstado();
            BigDecimal[] destino = (estado == Estado.MG) ? geracaoMG : geracaoES;

            // Geração já está em MWh
            destino[0] = destino[0].add(safe(geracao.getValorJaneiro()));
            destino[1] = destino[1].add(safe(geracao.getValorFevereiro()));
            destino[2] = destino[2].add(safe(geracao.getValorMarco()));
            destino[3] = destino[3].add(safe(geracao.getValorAbril()));
            destino[4] = destino[4].add(safe(geracao.getValorMaio()));
            destino[5] = destino[5].add(safe(geracao.getValorJunho()));
            destino[6] = destino[6].add(safe(geracao.getValorJulho()));
            destino[7] = destino[7].add(safe(geracao.getValorAgosto()));
            destino[8] = destino[8].add(safe(geracao.getValorSetembro()));
            destino[9] = destino[9].add(safe(geracao.getValorOutubro()));
            destino[10] = destino[10].add(safe(geracao.getValorNovembro()));
            destino[11] = destino[11].add(safe(geracao.getValorDezembro()));
        }

        // 4. Calcular resultados por estado
        CargaEstadoDTO resultadoMG = calcularCargaEstado(consumoMG, geracaoMG);
        CargaEstadoDTO resultadoES = calcularCargaEstado(consumoES, geracaoES);

        // 5. Calcular totais Samarco (MG + ES)
        BigDecimal[] consumoTotal = new BigDecimal[12];
        BigDecimal[] geracaoTotal = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            consumoTotal[i] = consumoMG[i].add(consumoES[i]);
            geracaoTotal[i] = geracaoMG[i].add(geracaoES[i]);
        }
        CargaEstadoDTO resultadoTotal = calcularCargaEstado(consumoTotal, geracaoTotal);

        // 6. Calcular percentuais de consumo
        BigDecimal consumoTotalAnual = resultadoTotal.getConsumoTotal();
        if (consumoTotalAnual.compareTo(ZERO) > 0) {
            resultadoMG.setPercentualConsumo(resultadoMG.getConsumoTotal().divide(consumoTotalAnual, 10, RoundingMode.HALF_UP));
            resultadoES.setPercentualConsumo(resultadoES.getConsumoTotal().divide(consumoTotalAnual, 10, RoundingMode.HALF_UP));
            resultadoTotal.setPercentualConsumo(BigDecimal.ONE);
        }

        return DistribuicaoCargaResultDTO.builder()
                .revisaoId(revisaoId)
                .minasGerais(resultadoMG)
                .espiritoSanto(resultadoES)
                .totalSamarco(resultadoTotal)
                .percentualPerda(PERCENTUAL_PERDA)
                .build();
    }

    private CargaEstadoDTO calcularCargaEstado(BigDecimal[] consumo, BigDecimal[] geracao) {
        BigDecimal[] perda = new BigDecimal[12];
        BigDecimal[] necessidadeTotal = new BigDecimal[12];
        BigDecimal[] necessidadeCompra = new BigDecimal[12];

        for (int i = 0; i < 12; i++) {
            // Perda = Consumo × 3%
            perda[i] = consumo[i].multiply(PERCENTUAL_PERDA, DECIMAL64);

            // Necessidade Total = Consumo + Perdas
            necessidadeTotal[i] = consumo[i].add(perda[i]);

            // Necessidade Compra = MAX(Necessidade - Geração, 0)
            BigDecimal diff = necessidadeTotal[i].subtract(geracao[i]);
            necessidadeCompra[i] = diff.compareTo(ZERO) < 0 ? ZERO : diff;
        }

        return CargaEstadoDTO.builder()
                .consumoJaneiro(consumo[0])
                .consumoFevereiro(consumo[1])
                .consumoMarco(consumo[2])
                .consumoAbril(consumo[3])
                .consumoMaio(consumo[4])
                .consumoJunho(consumo[5])
                .consumoJulho(consumo[6])
                .consumoAgosto(consumo[7])
                .consumoSetembro(consumo[8])
                .consumoOutubro(consumo[9])
                .consumoNovembro(consumo[10])
                .consumoDezembro(consumo[11])
                .consumoTotal(somarArray(consumo))
                .geracaoJaneiro(geracao[0])
                .geracaoFevereiro(geracao[1])
                .geracaoMarco(geracao[2])
                .geracaoAbril(geracao[3])
                .geracaoMaio(geracao[4])
                .geracaoJunho(geracao[5])
                .geracaoJulho(geracao[6])
                .geracaoAgosto(geracao[7])
                .geracaoSetembro(geracao[8])
                .geracaoOutubro(geracao[9])
                .geracaoNovembro(geracao[10])
                .geracaoDezembro(geracao[11])
                .geracaoTotal(somarArray(geracao))
                .perdaJaneiro(perda[0])
                .perdaFevereiro(perda[1])
                .perdaMarco(perda[2])
                .perdaAbril(perda[3])
                .perdaMaio(perda[4])
                .perdaJunho(perda[5])
                .perdaJulho(perda[6])
                .perdaAgosto(perda[7])
                .perdaSetembro(perda[8])
                .perdaOutubro(perda[9])
                .perdaNovembro(perda[10])
                .perdaDezembro(perda[11])
                .perdaTotal(somarArray(perda))
                .necessidadeTotalJaneiro(necessidadeTotal[0])
                .necessidadeTotalFevereiro(necessidadeTotal[1])
                .necessidadeTotalMarco(necessidadeTotal[2])
                .necessidadeTotalAbril(necessidadeTotal[3])
                .necessidadeTotalMaio(necessidadeTotal[4])
                .necessidadeTotalJunho(necessidadeTotal[5])
                .necessidadeTotalJulho(necessidadeTotal[6])
                .necessidadeTotalAgosto(necessidadeTotal[7])
                .necessidadeTotalSetembro(necessidadeTotal[8])
                .necessidadeTotalOutubro(necessidadeTotal[9])
                .necessidadeTotalNovembro(necessidadeTotal[10])
                .necessidadeTotalDezembro(necessidadeTotal[11])
                .necessidadeTotal(somarArray(necessidadeTotal))
                .necessidadeCompraJaneiro(necessidadeCompra[0])
                .necessidadeCompraFevereiro(necessidadeCompra[1])
                .necessidadeCompraMarco(necessidadeCompra[2])
                .necessidadeCompraAbril(necessidadeCompra[3])
                .necessidadeCompraMaio(necessidadeCompra[4])
                .necessidadeCompraJunho(necessidadeCompra[5])
                .necessidadeCompraJulho(necessidadeCompra[6])
                .necessidadeCompraAgosto(necessidadeCompra[7])
                .necessidadeCompraSetembro(necessidadeCompra[8])
                .necessidadeCompraOutubro(necessidadeCompra[9])
                .necessidadeCompraNovembro(necessidadeCompra[10])
                .necessidadeCompraDezembro(necessidadeCompra[11])
                .necessidadeCompraTotal(somarArray(necessidadeCompra))
                .build();
    }

    private BigDecimal somarArray(BigDecimal[] arr) {
        BigDecimal total = ZERO;
        for (BigDecimal v : arr) {
            total = total.add(v);
        }
        return total;
    }

    private BigDecimal dividirPorMil(BigDecimal valor) {
        if (valor == null) return ZERO;
        return valor.divide(MIL, DECIMAL64);
    }

    private BigDecimal safe(BigDecimal valor) {
        return valor != null ? valor : ZERO;
    }
}
