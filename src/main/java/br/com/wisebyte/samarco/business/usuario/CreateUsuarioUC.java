package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateUsuarioUC {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    UsuarioValidationBusiness validator;

    @Transactional
    public UsuarioDTO create( @NotNull UsuarioDTO dto ) {
        if ( validator.usuarioIsNullOrEmpty( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario não deve ser nulo ou vazio" );
        }
        if ( validator.existeUsuario( dto ) ) {
            throw new ValidadeExceptionBusiness( "Usuario", "Usuario", "Usuario já existe" );
        }
        return usuarioMapper.toDTO( usuarioRepository.save( usuarioMapper.toEntity( dto ) ) );
    }
}
