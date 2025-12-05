package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import br.com.wisebyte.samarco.model.usuario._Usuario;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QueryUsuarioUC {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioMapper usuarioMapper;

    public QueryList<UsuarioDTO> findUsers( Integer page, Integer size ) {
        Page<Usuario> all = usuarioRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Usuario.usuario.asc( ) ) );
        return QueryList.<UsuarioDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( usuarioMapper::toDTO ).toList( ) )
                .build( );
    }

    public UsuarioDTO findUserById( String id ) {
        return usuarioRepository.findById( id )
                .map( usuarioMapper::toDTO )
                .orElse( null );
    }

    public UsuarioDTO findUserByName( String nome ) {
        return usuarioRepository.findByUsuario( nome )
                .map( usuarioMapper::toDTO )
                .orElse( null );
    }
}
