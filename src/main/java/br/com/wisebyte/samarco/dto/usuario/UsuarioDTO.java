package br.com.wisebyte.samarco.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    @NotNull
    private String usuario;

    private String senha;

    private String nome;
}
