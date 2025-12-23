package br.com.wisebyte.samarco.graphql.mutation.demanda;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.demanda.CreateDemandaUC;
import br.com.wisebyte.samarco.business.demanda.DeleteDemandaUC;
import br.com.wisebyte.samarco.business.demanda.UpdateDemandaUC;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DemandaMutation {

    @Inject
    CreateDemandaUC createUC;

    @Inject
    UpdateDemandaUC updateUC;

    @Inject
    DeleteDemandaUC deleteUC;

    @Mutation("cadastrarDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_DEMAND})
    public DemandaDTO cadastrarDemanda(DemandaDTO demanda) {
        return createUC.create(demanda);
    }

    @Mutation("alterarDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_DEMAND})
    public DemandaDTO alterarDemanda(DemandaDTO demanda) {
        return updateUC.update(demanda);
    }

    @Mutation("excluirDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_DEMAND})
    public DemandaDTO excluirDemanda(DemandaDTO demanda) {
        deleteUC.delete(demanda);
        return demanda;
    }
}
