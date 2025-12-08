package br.com.wisebyte.samarco.graphql.query.usuario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.usuario.QueryUsuarioUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UsuarioQuery {

    @Inject
    QueryUsuarioUC queryUsuarioUC;

    @Query( value = "users" )
    @SecuredAccess( roles = {ADMIN}, permissionsRequired = {LIST_USERS} )
    public QueryList<UsuarioDTO> listarUsuariosPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryUsuarioUC.findUsers( page, size );
    }

    @Query( value = "buscarUsuarioPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_USER_BY_ID} )
    public UsuarioDTO buscarUsuarioPorId( String id ) {
        return queryUsuarioUC.findUserById( id );
    }

    @Query( value = "buscarUsuarioPorNome" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_USER_BY_NAME} )
    public UsuarioDTO buscarUsuarioPorNome( String usuario ) {
        return queryUsuarioUC.findUserByName( usuario );
    }
}
