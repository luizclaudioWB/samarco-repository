package br.com.wisebyte.samarco.graphql.query.usuario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.usuario.ListUsuarioUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import br.com.wisebyte.samarco.mapper.usuario.UsuarioMapper;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.*;
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

    @Inject
    ListUsuarioUC listUsuarioUC;

    @Query( value = "listarUsuariosPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_USERS} )
    public ListResults<UsuarioDTO> listarUsuariosPaginado(
            List<FilterInput> filters,
            String sortBy,
            SortDirectionDTO sortDirection,
            @NotNull Integer page,
            @NotNull Integer size ) {
        Map<String, Object> finalFilters = Optional.ofNullable( filters )
                .orElse( Collections.emptyList() )
                .stream()
                .filter( f -> f.key() != null && f.value() != null )
                .collect( Collectors.toMap( FilterInput::key, FilterInput::value, ( a, b ) -> b ) );

        String column = Optional.ofNullable( sortBy ).orElse( "usuario" );
        String sortDir = Optional.ofNullable( sortDirection )
                .map( SortDirectionDTO::name )
                .orElse( "ASC" );

        return usuarioMapper.toDTO( listUsuarioUC.list( finalFilters, column, sortDir, page, size ) );
    }

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
