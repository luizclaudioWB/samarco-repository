package br.com.wisebyte.samarco.dto.usuario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private String usuario;

    private String senha;

    private String nome;
}
