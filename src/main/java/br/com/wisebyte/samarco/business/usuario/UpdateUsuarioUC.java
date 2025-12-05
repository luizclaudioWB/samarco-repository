package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateUsuarioUC {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    UsuarioValidationBusiness validator;

    @Transactional
    public UsuarioDTO update( UsuarioDTO dto ) {
        if ( validator.usuarioIsNullOrEmpty( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario não deve ser nulo ou vazio" );
        }
        if ( !validator.existeUsuario( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario não encontrado" );
        }
        Usuario usuario = usuarioRepository.findByUsuario( dto.getUsuario( ) ).orElseThrow( );
        applyNewValues( usuario, dto );
        return usuarioMapper.toDTO( usuarioRepository.save( usuario ) );
    }

    private void applyNewValues( Usuario usuario, UsuarioDTO dto ) {
        usuario.setSenha( dto.getSenha( ) );
        usuario.setNome( dto.getNome( ) );
    }
}
