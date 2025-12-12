package br.com.wisebyte.samarco.dto.distribuidora;

import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistribuidoraDTO {

    private Long id;

    @NotNull
    private String nome;

    private String cnpj;

    @NotNull
    private String siglaAgente;

    @NotNull
    private Estado estado;
}
