package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteUsuarioUC {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioValidationBusiness validator;

    @Transactional
    public void delete( UsuarioDTO dto ) {
        if ( validator.userIsNullOrEmpty( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario não deve ser nulo ou vazio" );
        }
        if ( !validator.userExists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario não encontrado" );
        }
        Usuario usuario = usuarioRepository.findByUsuario( dto.getUsuario( ) ).orElseThrow( );
        usuarioRepository.delete( usuario );
    }

}
