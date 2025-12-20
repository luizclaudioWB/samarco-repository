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
    CreateDemandaUC createDemandaUC;

    @Inject
    UpdateDemandaUC updateDemandaUC;

    @Inject
    DeleteDemandaUC deleteDemandaUC;

    @Mutation(value = "cadastrarDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_DEMAND})
    public DemandaDTO cadastrarDemanda(DemandaDTO dto) {
        return createDemandaUC.create(dto);
    }

    @Mutation(value = "alterarDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_DEMAND})
    public DemandaDTO alterarDemanda(DemandaDTO dto) {
        return updateDemandaUC.update(dto);
    }

    @Mutation(value = "excluirDemanda")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_DEMAND})
    public DemandaDTO excluirDemanda(DemandaDTO dto) {
        deleteDemandaUC.delete(dto);
        return dto;
    }
}
