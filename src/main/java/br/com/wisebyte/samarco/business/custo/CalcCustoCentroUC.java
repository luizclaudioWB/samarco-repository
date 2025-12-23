package br.com.wisebyte.samarco.business.custo;

import br.com.wisebyte.samarco.business.consumo.CalcConsumoAreaUC;
import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoCentroResultDTO;
import br.com.wisebyte.samarco.dto.custo.CustoCentroResultDTO.CentroCustoDTO;
import br.com.wisebyte.samarco.dto.custo.CustoCentroResultDTO.TotalEstadoDTO;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
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

/**
 * Calcula o Custo por Centro de Custo.
 * Baseado na Sheet 15 da planilha - "Distrib Centro de Custos".
 *
 * Fórmula: Custo CC = Custo Classe × (Consumo CC / Consumo Total Estado)
 */
@ApplicationScoped
public class CalcCustoCentroUC {

    private static final BigDecimal MIL = new BigDecimal("1000");

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalcConsumoAreaUC calcConsumoAreaUC;

    @Inject
    CalcCustoClasseUC calcCustoClasseUC;

    @Inject
    AreaRepository areaRepository;

    public CustoCentroResultDTO calcular(@NotNull Long revisaoId) {
        revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("CustoCentro", "Revisao", "Revisao nao encontrada"));

        // 1. Buscar consumo por área
        var consumoAreas = calcConsumoAreaUC.calcConsumoArea(revisaoId).getResults();

        // 2. Buscar custo por classe
        CustoClasseResultDTO custoClasse = calcCustoClasseUC.calcular(revisaoId);

        // 3. Separar consumo por estado e calcular totais
        BigDecimal consumoTotalMG = ZERO;
        BigDecimal consumoTotalES = ZERO;
        List<ConsumoAreaComEstado> consumosComEstado = new ArrayList<>();

        for (ConsumoAreaResultDTO consumo : consumoAreas) {
            var area = areaRepository.findById(consumo.getAreaId()).orElse(null);
            if (area == null) continue;

            Estado estado = area.getUnidade().getEstado();
            BigDecimal consumoTotal = calcularConsumoTotalMWh(consumo);

            consumosComEstado.add(new ConsumoAreaComEstado(consumo, area.getCentroCusto(), estado, consumoTotal));

            if (estado == Estado.MG) {
                consumoTotalMG = consumoTotalMG.add(consumoTotal);
            } else {
                consumoTotalES = consumoTotalES.add(consumoTotal);
            }
        }

        // 4. Calcular custos por centro de custo
        List<CentroCustoDTO> centrosMG = new ArrayList<>();
        List<CentroCustoDTO> centrosES = new ArrayList<>();

        for (ConsumoAreaComEstado cae : consumosComEstado) {
            Estado estado = cae.estado;
            BigDecimal consumoEstado = (estado == Estado.MG) ? consumoTotalMG : consumoTotalES;
            var custoEstado = (estado == Estado.MG) ? custoClasse.getMinasGerais() : custoClasse.getEspiritoSanto();

            if (custoEstado == null || consumoEstado.compareTo(ZERO) == 0) continue;

            // Calcular percentual de rateio
            BigDecimal percentualRateio = cae.consumoTotal.divide(consumoEstado, 10, RoundingMode.HALF_UP);

            // Ratear custos
            CentroCustoDTO centro = CentroCustoDTO.builder()
                    .areaId(cae.consumo.getAreaId())
                    .nomeArea(cae.consumo.getNomeArea())
                    .centroCusto(cae.centroCusto)
                    .percentualRateio(percentualRateio)
                    .custoJaneiro(ratear(custoEstado.getTotalJaneiro(), percentualRateio))
                    .custoFevereiro(ratear(custoEstado.getTotalFevereiro(), percentualRateio))
                    .custoMarco(ratear(custoEstado.getTotalMarco(), percentualRateio))
                    .custoAbril(ratear(custoEstado.getTotalAbril(), percentualRateio))
                    .custoMaio(ratear(custoEstado.getTotalMaio(), percentualRateio))
                    .custoJunho(ratear(custoEstado.getTotalJunho(), percentualRateio))
                    .custoJulho(ratear(custoEstado.getTotalJulho(), percentualRateio))
                    .custoAgosto(ratear(custoEstado.getTotalAgosto(), percentualRateio))
                    .custoSetembro(ratear(custoEstado.getTotalSetembro(), percentualRateio))
                    .custoOutubro(ratear(custoEstado.getTotalOutubro(), percentualRateio))
                    .custoNovembro(ratear(custoEstado.getTotalNovembro(), percentualRateio))
                    .custoDezembro(ratear(custoEstado.getTotalDezembro(), percentualRateio))
                    .custoTotal(ratear(custoEstado.getTotalGeral(), percentualRateio))
                    .consumoEnergiaTotal(ratear(custoEstado.getConsumoEnergiaTotal(), percentualRateio))
                    .usoRedeTotal(ratear(custoEstado.getUsoRedeTotal(), percentualRateio))
                    .encargosTotal(ratear(custoEstado.getEncargosTotal(), percentualRateio))
                    .build();

            if (estado == Estado.MG) {
                centrosMG.add(centro);
            } else {
                centrosES.add(centro);
            }
        }

        // 5. Calcular totais
        TotalEstadoDTO totalMG = calcularTotal(centrosMG);
        TotalEstadoDTO totalES = calcularTotal(centrosES);
        TotalEstadoDTO totalSamarco = TotalEstadoDTO.builder()
                .consumoEnergiaTotal(safe(totalMG.getConsumoEnergiaTotal()).add(safe(totalES.getConsumoEnergiaTotal())))
                .usoRedeTotal(safe(totalMG.getUsoRedeTotal()).add(safe(totalES.getUsoRedeTotal())))
                .encargosTotal(safe(totalMG.getEncargosTotal()).add(safe(totalES.getEncargosTotal())))
                .totalGeral(safe(totalMG.getTotalGeral()).add(safe(totalES.getTotalGeral())))
                .build();

        return CustoCentroResultDTO.builder()
                .revisaoId(revisaoId)
                .centrosCustoMG(centrosMG)
                .centrosCustoES(centrosES)
                .totalMG(totalMG)
                .totalES(totalES)
                .totalSamarco(totalSamarco)
                .build();
    }

    private BigDecimal calcularConsumoTotalMWh(ConsumoAreaResultDTO consumo) {
        BigDecimal total = ZERO;
        total = total.add(dividirPorMil(consumo.getValorJaneiro()));
        total = total.add(dividirPorMil(consumo.getValorFevereiro()));
        total = total.add(dividirPorMil(consumo.getValorMarco()));
        total = total.add(dividirPorMil(consumo.getValorAbril()));
        total = total.add(dividirPorMil(consumo.getValorMaio()));
        total = total.add(dividirPorMil(consumo.getValorJunho()));
        total = total.add(dividirPorMil(consumo.getValorJulho()));
        total = total.add(dividirPorMil(consumo.getValorAgosto()));
        total = total.add(dividirPorMil(consumo.getValorSetembro()));
        total = total.add(dividirPorMil(consumo.getValorOutubro()));
        total = total.add(dividirPorMil(consumo.getValorNovembro()));
        total = total.add(dividirPorMil(consumo.getValorDezembro()));
        return total;
    }

    private TotalEstadoDTO calcularTotal(List<CentroCustoDTO> centros) {
        BigDecimal consumoEnergia = ZERO;
        BigDecimal usoRede = ZERO;
        BigDecimal encargos = ZERO;
        BigDecimal total = ZERO;

        for (CentroCustoDTO centro : centros) {
            consumoEnergia = consumoEnergia.add(safe(centro.getConsumoEnergiaTotal()));
            usoRede = usoRede.add(safe(centro.getUsoRedeTotal()));
            encargos = encargos.add(safe(centro.getEncargosTotal()));
            total = total.add(safe(centro.getCustoTotal()));
        }

        return TotalEstadoDTO.builder()
                .consumoEnergiaTotal(consumoEnergia)
                .usoRedeTotal(usoRede)
                .encargosTotal(encargos)
                .totalGeral(total)
                .build();
    }

    private BigDecimal ratear(BigDecimal valor, BigDecimal percentual) {
        if (valor == null) return ZERO;
        return valor.multiply(percentual, DECIMAL64);
    }

    private BigDecimal dividirPorMil(BigDecimal valor) {
        if (valor == null) return ZERO;
        return valor.divide(MIL, DECIMAL64);
    }

    private BigDecimal safe(BigDecimal valor) {
        return valor != null ? valor : ZERO;
    }

    private record ConsumoAreaComEstado(ConsumoAreaResultDTO consumo, String centroCusto, Estado estado, BigDecimal consumoTotal) {}
}
