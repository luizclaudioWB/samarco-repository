package br.com.wisebyte.samarco.business.custo;

import br.com.wisebyte.samarco.business.consumo.CalcConsumoAreaUC;
import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoEnergiaResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoEnergiaResultDTO.CustoAreaDTO;
import br.com.wisebyte.samarco.model.geracao.PlanejamentoGeracao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL64;

@ApplicationScoped
public class CalcCustoEnergiaUC {

    private static final BigDecimal MIL = new BigDecimal("1000");

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalcConsumoAreaUC calcConsumoAreaUC;

    @Inject
    PlanejamentoGeracaoRepository geracaoRepository;

    @Inject
    AreaRepository areaRepository;

    public CustoEnergiaResultDTO calcularCustoEnergia(@NotNull Long revisaoId) {
        revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("CustoEnergia", "Revisao", "Revisao nao encontrada"));

        // 1. Buscar consumo por area (em kWh)
        var consumoAreas = calcConsumoAreaUC.calcConsumoArea(revisaoId).getResults();

        // 2. Buscar geracao total (em MWh)
        List<PlanejamentoGeracao> geracoes = geracaoRepository.findByRevisaoId(revisaoId);
        BigDecimal geracaoTotalMWh = calcularGeracaoTotal(geracoes);

        // 3. Converter consumo para MWh e calcular total
        BigDecimal consumoTotalMWh = ZERO;
        List<CustoAreaDTO> custosAreas = new ArrayList<>();

        for (ConsumoAreaResultDTO consumo : consumoAreas) {
            var area = areaRepository.findById(consumo.getArea().getId()).orElse(null);

            BigDecimal consumoMWhJan = dividirPorMil(consumo.getConsumoJaneiro());
            BigDecimal consumoMWhFev = dividirPorMil(consumo.getConsumoFevereiro());
            BigDecimal consumoMWhMar = dividirPorMil(consumo.getConsumoMarco());
            BigDecimal consumoMWhAbr = dividirPorMil(consumo.getConsumoAbril());
            BigDecimal consumoMWhMai = dividirPorMil(consumo.getConsumoMaio());
            BigDecimal consumoMWhJun = dividirPorMil(consumo.getConsumoJunho());
            BigDecimal consumoMWhJul = dividirPorMil(consumo.getConsumoJulho());
            BigDecimal consumoMWhAgo = dividirPorMil(consumo.getConsumoAgosto());
            BigDecimal consumoMWhSet = dividirPorMil(consumo.getConsumoSetembro());
            BigDecimal consumoMWhOut = dividirPorMil(consumo.getConsumoOutubro());
            BigDecimal consumoMWhNov = dividirPorMil(consumo.getConsumoNovembro());
            BigDecimal consumoMWhDez = dividirPorMil(consumo.getConsumoDezembro());

            BigDecimal totalArea = consumoMWhJan.add(consumoMWhFev).add(consumoMWhMar)
                    .add(consumoMWhAbr).add(consumoMWhMai).add(consumoMWhJun)
                    .add(consumoMWhJul).add(consumoMWhAgo).add(consumoMWhSet)
                    .add(consumoMWhOut).add(consumoMWhNov).add(consumoMWhDez);

            consumoTotalMWh = consumoTotalMWh.add(totalArea);

            custosAreas.add(CustoAreaDTO.builder()
                    .areaId(consumo.getArea().getId())
                    .nomeArea(consumo.getNomeArea())
                    .tipoArea(consumo.getTipoArea())
                    .centroCusto(area != null ? area.getCentroCusto() : null)
                    .consumoMWhJaneiro(consumoMWhJan)
                    .consumoMWhFevereiro(consumoMWhFev)
                    .consumoMWhMarco(consumoMWhMar)
                    .consumoMWhAbril(consumoMWhAbr)
                    .consumoMWhMaio(consumoMWhMai)
                    .consumoMWhJunho(consumoMWhJun)
                    .consumoMWhJulho(consumoMWhJul)
                    .consumoMWhAgosto(consumoMWhAgo)
                    .consumoMWhSetembro(consumoMWhSet)
                    .consumoMWhOutubro(consumoMWhOut)
                    .consumoMWhNovembro(consumoMWhNov)
                    .consumoMWhDezembro(consumoMWhDez)
                    .consumoMWhTotal(totalArea)
                    .build());
        }

        // 4. Calcular necessidade de compra
        BigDecimal necessidadeCompra = consumoTotalMWh.subtract(geracaoTotalMWh);
        if (necessidadeCompra.compareTo(ZERO) < 0) {
            necessidadeCompra = ZERO;
        }

        // 5. Calcular percentual de rateio por area
        for (CustoAreaDTO custoArea : custosAreas) {
            if (consumoTotalMWh.compareTo(ZERO) > 0) {
                BigDecimal percentual = custoArea.getConsumoMWhTotal()
                        .divide(consumoTotalMWh, 10, RoundingMode.HALF_UP);
                custoArea.setPercentualRateio(percentual);
            } else {
                custoArea.setPercentualRateio(ZERO);
            }
        }

        return CustoEnergiaResultDTO.builder()
                .revisaoId(revisaoId)
                .custosPorArea(custosAreas)
                .totalConsumoMWh(consumoTotalMWh)
                .totalGeracaoMWh(geracaoTotalMWh)
                .totalNecessidadeCompraMWh(necessidadeCompra)
                .build();
    }

    private BigDecimal calcularGeracaoTotal(List<PlanejamentoGeracao> geracoes) {
        BigDecimal total = ZERO;
        for (PlanejamentoGeracao g : geracoes) {
            total = total.add(safe(g.getValorJaneiro()));
            total = total.add(safe(g.getValorFevereiro()));
            total = total.add(safe(g.getValorMarco()));
            total = total.add(safe(g.getValorAbril()));
            total = total.add(safe(g.getValorMaio()));
            total = total.add(safe(g.getValorJunho()));
            total = total.add(safe(g.getValorJulho()));
            total = total.add(safe(g.getValorAgosto()));
            total = total.add(safe(g.getValorSetembro()));
            total = total.add(safe(g.getValorOutubro()));
            total = total.add(safe(g.getValorNovembro()));
            total = total.add(safe(g.getValorDezembro()));
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
