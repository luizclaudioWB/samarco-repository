package br.com.wisebyte.samarco.dto.planejamento;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanejamentoDTO {

    private Long id;

    private Integer ano;

    private String descricao;

    private String mensagem;

    private Boolean corrente;

    private String usuarioId;
}
