package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QueryUnidadeUC {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> findUsers( ) {
        return usuarioRepository.findAll( )
                .map( usuarioMapper::toDTO )
                .collect( Collectors.toList( ) );
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
