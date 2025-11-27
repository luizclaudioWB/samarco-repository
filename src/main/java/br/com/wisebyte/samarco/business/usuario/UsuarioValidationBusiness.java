package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioValidationBusiness {

    @Inject
    UsuarioRepository usuarioRepository;

    public boolean usuarioIsNullOrEmpty( UsuarioDTO dto ) {
        return dto.getUsuario( ) == null || dto.getUsuario( ).isBlank( );
    }

    public boolean existeUsuario( UsuarioDTO dto ) {
        return usuarioRepository.findByUsuario( dto.getUsuario( ) ).isPresent( );
    }

}
