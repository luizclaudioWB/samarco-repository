package br.com.wisebyte.samarco.dto.prducao;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TablePlanejamentoProducaoDTO {

    PlanejamentoProducaoDTO planejamentoProducao;

    private BigDecimal valorPlanejadoJaneiro;

    private BigDecimal valorPlanejadoFevereiro;

    private BigDecimal valorPlanejadoMarco;

    private BigDecimal valorPlanejadoAbril;

    private BigDecimal valorPlanejadoMaio;

    private BigDecimal valorPlanejadoJunho;

    private BigDecimal valorPlanejadoJulho;

    private BigDecimal valorPlanejadoAgosto;

    private BigDecimal valorPlanejadoSetembro;

    private BigDecimal valorPlanejadoOutubro;

    private BigDecimal valorPlanejadoNovembro;

    private BigDecimal valorPlanejadoDezembro;
}
