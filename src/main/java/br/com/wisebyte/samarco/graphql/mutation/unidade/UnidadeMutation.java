package br.com.wisebyte.samarco.graphql.mutation.unidade;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.unidade.CreateUnidadeUC;
import br.com.wisebyte.samarco.business.unidade.DeleteUnidadeUC;
import br.com.wisebyte.samarco.business.unidade.UpdateUnidadeUC;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UnidadeMutation {

    @Inject
    CreateUnidadeUC cadastrarUnidade;

    @Inject
    UpdateUnidadeUC alterarUnidade;

    @Inject
    DeleteUnidadeUC excluirUnidade;

    @Mutation( value = "cadastrarUnidade" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_UNIT} )
    public UnidadeDTO cadastrarUnidade( UnidadeDTO unidade ) {
        return cadastrarUnidade.create( unidade );
    }

    @Mutation( value = "alterarUnidade" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_UNIT} )
    public UnidadeDTO alterarUnidade( UnidadeDTO unidade ) {
        return alterarUnidade.update( unidade );
    }

    @Mutation( value = "excluirUnidade" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_UNIT} )
    public UnidadeDTO excluirUnidade( UnidadeDTO unidade ) {
        excluirUnidade.delete( unidade );
        return unidade;
    }
}
