package br.com.wisebyte.samarco.dto.distribuidora;

import br.com.wisebyte.samarco.model.estado.Estado;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistribuidoraDTO {

    private Long id;

    private String nome;

    private String cnpj;

    private String siglaAgente;

    private Estado estado;
}
