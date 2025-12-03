package br.com.wisebyte.samarco.mapper.usuario;
import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioMapper implements EntityMapper<Usuario, UsuarioDTO> {

    @Override
    public Usuario toEntity( UsuarioDTO dto ) {
        return Usuario.builder( )
                .usuario( dto.getUsuario( ) )
                .senha( dto.getSenha( ) )
                .nome( dto.getNome( ) )
                .build( );
    }

    @Override
    public UsuarioDTO toDTO( Usuario entity ) {
        return UsuarioDTO.builder( )
                .usuario( entity.getUsuario( ) )
                .senha( entity.getSenha( ) )
                .nome( entity.getNome( ) )
                .build( );
    }
}
