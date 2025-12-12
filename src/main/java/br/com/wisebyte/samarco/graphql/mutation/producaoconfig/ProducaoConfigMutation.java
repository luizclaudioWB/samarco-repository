package br.com.wisebyte.samarco.graphql.mutation.producaoconfig;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producaoconfig.CreateProducaoConfigUC;
import br.com.wisebyte.samarco.business.producaoconfig.DeleteProducaoConfigUC;
import br.com.wisebyte.samarco.business.producaoconfig.UpdateProducaoConfigUC;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ProducaoConfigMutation {

    @Inject
    CreateProducaoConfigUC createUC;

    @Inject
    UpdateProducaoConfigUC updateUC;

    @Inject
    DeleteProducaoConfigUC deleteUC;

    @Mutation("cadastrarProducaoConfig")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {CREATE_PRODUCTION_CONFIG}
    )
    public ProducaoConfigDTO cadastrarProducaoConfig(ProducaoConfigDTO producaoConfig) {
        return createUC.create(producaoConfig);
    }

    @Mutation("alterarProducaoConfig")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {UPDATE_PRODUCTION_CONFIG}
    )
    public ProducaoConfigDTO alterarProducaoConfig(ProducaoConfigDTO producaoConfig) {
        return updateUC.update(producaoConfig);
    }

    @Mutation("excluirProducaoConfig")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {DELETE_PRODUCTION_CONFIG}
    )
    public ProducaoConfigDTO excluirProducaoConfig(ProducaoConfigDTO producaoConfig) {
        deleteUC.delete(producaoConfig);
        return producaoConfig;
    }
}
