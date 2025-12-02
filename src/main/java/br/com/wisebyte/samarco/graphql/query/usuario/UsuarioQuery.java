package br.com.wisebyte.samarco.graphql.query.usuario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UsuarioQuery {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioMapper usuarioMapper;

    @Query( value = "listarUsuarios" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_USERS} )
    public List<UsuarioDTO> listarUsuarios( ) {
        return usuarioRepository.findAll( )
                .map( usuarioMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarUsuarioPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_USER_BY_ID} )
    public UsuarioDTO buscarUsuarioPorId( String id ) {
        return usuarioRepository.findById( id )
                .map( usuarioMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "buscarUsuarioPorNome" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_USER_BY_NAME} )
    public UsuarioDTO buscarUsuarioPorNome( String usuario ) {
        return usuarioRepository.findByUsuario( usuario )
                .map( usuarioMapper::toDTO )
                .orElse( null );
    }
}
