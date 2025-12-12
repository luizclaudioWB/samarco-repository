package br.com.wisebyte.samarco.dto.tarifa;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long tarifaPlanejamentoId;

    @NotNull
    private Long distribuidoraId;

    @NotNull
    private LocalDate periodoInicial;

    @NotNull
    private LocalDate periodoFinal;

    @NotNull
    private BigDecimal valorPonta;

    @NotNull
    private BigDecimal valorForaPonta;

    @NotNull
    private BigDecimal valorEncargos;

    @NotNull
    private BigDecimal valorEncargosAutoProducao;

    @NotNull
    private BigDecimal percentualPisCofins;

    private boolean sobrescreverICMS;

    private BigDecimal percentualICMS;

    @NotNull
    private Integer qtdeDeHorasPonta;
}
