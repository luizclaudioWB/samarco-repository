package br.com.wisebyte.samarco.dto.revisao;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevisaoInputDTO {

    private Long id;

    @NotNull
    private Integer numeroRevisao;

    private String usuarioId;

    @NotNull
    private Long planejamentoId;

    @NotNull
    private String descricao;
}
