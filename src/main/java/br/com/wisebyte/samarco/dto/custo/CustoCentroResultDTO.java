package br.com.wisebyte.samarco.dto.custo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Resultado do cálculo de Custo por Centro de Custo.
 * Baseado na Sheet 15 da planilha - "Distrib Centro de Custos".
 *
 * Fórmula: Custo CC = Custo Classe × (Consumo CC / Consumo Total Estado)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustoCentroResultDTO {

    private Long revisaoId;

    // Custos por centro de custo de Minas Gerais
    private List<CentroCustoDTO> centrosCustoMG;

    // Custos por centro de custo de Espírito Santo
    private List<CentroCustoDTO> centrosCustoES;

    // Totais por estado
    private TotalEstadoDTO totalMG;
    private TotalEstadoDTO totalES;
    private TotalEstadoDTO totalSamarco;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CentroCustoDTO {
        private Long areaId;
        private String nomeArea;
        private String centroCusto;

        // Percentual de rateio = Consumo CC / Consumo Estado
        private BigDecimal percentualRateio;

        // Custos mensais (já rateados)
        private BigDecimal custoJaneiro;
        private BigDecimal custoFevereiro;
        private BigDecimal custoMarco;
        private BigDecimal custoAbril;
        private BigDecimal custoMaio;
        private BigDecimal custoJunho;
        private BigDecimal custoJulho;
        private BigDecimal custoAgosto;
        private BigDecimal custoSetembro;
        private BigDecimal custoOutubro;
        private BigDecimal custoNovembro;
        private BigDecimal custoDezembro;
        private BigDecimal custoTotal;

        // Detalhamento por classe
        private BigDecimal consumoEnergiaTotal;  // 50610002
        private BigDecimal usoRedeTotal;         // 50610003
        private BigDecimal encargosTotal;        // 50610006
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalEstadoDTO {
        private BigDecimal consumoEnergiaTotal;  // 50610002
        private BigDecimal usoRedeTotal;         // 50610003
        private BigDecimal encargosTotal;        // 50610006
        private BigDecimal totalGeral;
    }
}
