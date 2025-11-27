package br.com.wisebyte.samarco.graphql.mutation.usuario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.usuario.CreateUsuarioUC;
import br.com.wisebyte.samarco.business.usuario.DeleteUsuarioUC;
import br.com.wisebyte.samarco.business.usuario.UpdateUsuarioUC;
import br.com.wisebyte.samarco.dto.usuario.UsuarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UsuarioMutation {

    @Inject
    CreateUsuarioUC cadastrarUsuario;

    @Inject
    UpdateUsuarioUC alterarUsuario;

    @Inject
    DeleteUsuarioUC excluirUsuario;

    @Mutation( value = "cadastrarUsuario" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_USUARIO} )
    public UsuarioDTO cadastrarUsuario( UsuarioDTO dto ) {
        return cadastrarUsuario.create( dto );
    }

    @Mutation( value = "alterarUsuario" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_USUARIO} )
    public UsuarioDTO alterarUsuario( UsuarioDTO dto ) {
        return alterarUsuario.update( dto );
    }

    @Mutation( value = "excluirUsuario" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_USUARIO} )
    public UsuarioDTO excluirUsuario( UsuarioDTO dto ) {
        excluirUsuario.delete( dto );
        return dto;
    }
}
