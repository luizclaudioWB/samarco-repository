package br.com.wisebyte.samarco.dto.revisao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevisaoDTO {

    private Long id;

    private Integer numeroRevisao;

    private String usuarioId;

    private Long planejamentoId;

    private String descricao;

    private boolean oficial;

    private boolean finished;
}
