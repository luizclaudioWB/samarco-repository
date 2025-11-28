package br.com.wisebyte.samarco.graphql.mutation.area;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.area.CreateAreaUC;
import br.com.wisebyte.samarco.business.area.DeleteAreaUC;
import br.com.wisebyte.samarco.business.area.UpdateAreaUC;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class AreaMutation {

    @Inject
    CreateAreaUC cadastrarArea;

    @Inject
    UpdateAreaUC alterarArea;

    @Inject
    DeleteAreaUC excluirArea;

    @Mutation( value = "cadastrarArea" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_AREA} )
    public AreaDTO cadastrarArea( AreaDTO dto ) {
        return cadastrarArea.create( dto );
    }

    @Mutation( value = "alterarArea" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_AREA} )
    public AreaDTO alterarArea( AreaDTO dto ) {
        return alterarArea.update( dto );
    }

    @Mutation( value = "excluirArea" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_AREA} )
    public AreaDTO excluirArea( AreaDTO dto ) {
        excluirArea.delete( dto );
        return dto;
    }
}
