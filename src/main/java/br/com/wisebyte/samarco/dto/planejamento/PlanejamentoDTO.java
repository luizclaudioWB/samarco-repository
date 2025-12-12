package br.com.wisebyte.samarco.dto.planejamento;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanejamentoDTO {

    private Long id;

    @NotNull
    private Integer ano;

    @NotNull
    private String descricao;

    private String mensagem;

    private Boolean corrente;
}
