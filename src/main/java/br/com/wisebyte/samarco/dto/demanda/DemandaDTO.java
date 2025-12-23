package br.com.wisebyte.samarco.dto.demanda;

import br.com.wisebyte.samarco.model.demanda.TipoHorario;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandaDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    @NotNull
    private Long unidadeId;

    @NotNull
    private TipoHorario tipoHorario;

    private BigDecimal valorJaneiro;
    private BigDecimal valorFevereiro;
    private BigDecimal valorMarco;
    private BigDecimal valorAbril;
    private BigDecimal valorMaio;
    private BigDecimal valorJunho;
    private BigDecimal valorJulho;
    private BigDecimal valorAgosto;
    private BigDecimal valorSetembro;
    private BigDecimal valorOutubro;
    private BigDecimal valorNovembro;
    private BigDecimal valorDezembro;
}
