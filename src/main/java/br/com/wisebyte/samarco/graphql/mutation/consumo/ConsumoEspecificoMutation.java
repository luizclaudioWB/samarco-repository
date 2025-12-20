package br.com.wisebyte.samarco.graphql.mutation.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.CreateConsumoEspecificoUC;
import br.com.wisebyte.samarco.business.consumo.DeleteConsumoEspecificoUC;
import br.com.wisebyte.samarco.business.consumo.UpdateConsumoEspecificoUC;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ConsumoEspecificoMutation {

    @Inject
    CreateConsumoEspecificoUC createConsumoUC;

    @Inject
    UpdateConsumoEspecificoUC updateConsumoUC;

    @Inject
    DeleteConsumoEspecificoUC deleteConsumoUC;

    @Mutation(value = "cadastrarConsumoEspecifico")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_SPECIFIC_CONSUMPTION})
    public ConsumoEspecificoDTO cadastrarConsumoEspecifico(ConsumoEspecificoDTO dto) {
        return createConsumoUC.create(dto);
    }

    @Mutation(value = "alterarConsumoEspecifico")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_SPECIFIC_CONSUMPTION})
    public ConsumoEspecificoDTO alterarConsumoEspecifico(ConsumoEspecificoDTO dto) {
        return updateConsumoUC.update(dto);
    }

    @Mutation(value = "excluirConsumoEspecifico")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_SPECIFIC_CONSUMPTION})
    public ConsumoEspecificoDTO excluirConsumoEspecifico(ConsumoEspecificoDTO dto) {
        deleteConsumoUC.delete(dto);
        return dto;
    }
}
