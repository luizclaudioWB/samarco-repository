package br.com.wisebyte.samarco.dto.tarifa;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarifaPlanejamentoDTO {

    private Long id;

    @NotNull
    private Long revisaoId;
}
