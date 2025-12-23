package br.com.wisebyte.samarco.dto.calendario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarioDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    @NotNull
    private Integer qtdeHorasPontaDia;

    @NotNull
    private Integer diasNaoUteisJaneiro;
    @NotNull
    private Integer diasNaoUteisFevereiro;
    @NotNull
    private Integer diasNaoUteisMarco;
    @NotNull
    private Integer diasNaoUteisAbril;
    @NotNull
    private Integer diasNaoUteisMaio;
    @NotNull
    private Integer diasNaoUteisJunho;
    @NotNull
    private Integer diasNaoUteisJulho;
    @NotNull
    private Integer diasNaoUteisAgosto;
    @NotNull
    private Integer diasNaoUteisSetembro;
    @NotNull
    private Integer diasNaoUteisOutubro;
    @NotNull
    private Integer diasNaoUteisNovembro;
    @NotNull
    private Integer diasNaoUteisDezembro;
}
