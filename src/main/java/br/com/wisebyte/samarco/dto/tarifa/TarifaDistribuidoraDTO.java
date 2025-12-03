package br.com.wisebyte.samarco.dto.tarifa;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDistribuidoraDTO {

    private Long id;
    private Long tarifaPlanejamentoId;
    private Long distribuidoraId;
    private LocalDate periodoInicial;
    private LocalDate periodoFinal;
    private BigDecimal valorPonta;
    private BigDecimal valorForaPonta;
    private BigDecimal valorEncargos;
    private BigDecimal valorEncargosAutoProducao;
    private BigDecimal percentualPisCofins;
    private boolean sobrescreverICMS;
    private BigDecimal percentualICMS;
    private Integer qtdeDeHorasPonta;
}
