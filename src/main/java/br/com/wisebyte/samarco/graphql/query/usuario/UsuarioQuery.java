package br.com.wisebyte.samarco.graphql.query.usuario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.unidade.QueryUnidadeUC;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

import static br.com.wisebyte.samarco.auth.Permissao.LISTAR_USUARIO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UsuarioQuery {

    @Inject
    QueryUnidadeUC queryUnidadeUC;

    @Query( value = "listarUsuarios" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_USUARIO} )
    public List<UsuarioDTO> listarUsuarios( ) {
        return queryUnidadeUC.findUsers( );
    }

    @Query( value = "buscarUsuarioPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_USUARIO} )
    public UsuarioDTO buscarUsuarioPorId( String id ) {
        return queryUnidadeUC.findUserById( id );
    }

    @Query( value = "buscarUsuarioPorNome" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_USUARIO} )
    public UsuarioDTO buscarUsuarioPorNome( String usuario ) {
        return queryUnidadeUC.findUserByName( usuario );
    }
}
