package br.com.wisebyte.samarco.dto.custo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustoEnergiaResultDTO {

    private Long revisaoId;

    private List<CustoAreaDTO> custosPorArea;

    private BigDecimal totalConsumoMWh;
    private BigDecimal totalGeracaoMWh;
    private BigDecimal totalNecessidadeCompraMWh;

    private BigDecimal custoTotalConsumo;
    private BigDecimal custoTotalDemanda;
    private BigDecimal custoTotalEncargos;
    private BigDecimal custoTotal;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustoAreaDTO {
        private Long areaId;
        private String nomeArea;
        private String centroCusto;
        private String tipoArea;

        private BigDecimal consumoMWhJaneiro;
        private BigDecimal consumoMWhFevereiro;
        private BigDecimal consumoMWhMarco;
        private BigDecimal consumoMWhAbril;
        private BigDecimal consumoMWhMaio;
        private BigDecimal consumoMWhJunho;
        private BigDecimal consumoMWhJulho;
        private BigDecimal consumoMWhAgosto;
        private BigDecimal consumoMWhSetembro;
        private BigDecimal consumoMWhOutubro;
        private BigDecimal consumoMWhNovembro;
        private BigDecimal consumoMWhDezembro;
        private BigDecimal consumoMWhTotal;

        private BigDecimal percentualRateio;
        private BigDecimal custoRateado;
    }
}
