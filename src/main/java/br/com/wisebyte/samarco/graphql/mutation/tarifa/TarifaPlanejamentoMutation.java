package br.com.wisebyte.samarco.graphql.mutation.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.planejamento.CreateTarifaPlanejamentoUC;
import br.com.wisebyte.samarco.business.tarifa.planejamento.DeleteTarifaPlanejamentoUC;
import br.com.wisebyte.samarco.business.tarifa.planejamento.UpdateTarifaPlanejamentoUC;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;


@GraphQLApi
@RequestScoped
public class TarifaPlanejamentoMutation {

    @Inject
    CreateTarifaPlanejamentoUC createUC;

    @Inject
    UpdateTarifaPlanejamentoUC updateUC;

    @Inject
    DeleteTarifaPlanejamentoUC deleteUC;


    @Mutation("cadastrarTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_RATE_PLANNING}
    )
    public TarifaPlanejamentoDTO cadastrarTarifaPlanejamento(
            TarifaPlanejamentoDTO tarifaPlanejamento
    ) {
        return createUC.create(tarifaPlanejamento);
    }

    @Mutation("alterarTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_RATE_PLANNING}
    )
    public TarifaPlanejamentoDTO alterarTarifaPlanejamento(
            TarifaPlanejamentoDTO tarifaPlanejamento
    ) {
        return updateUC.update(tarifaPlanejamento);
    }

    @Mutation("excluirTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_RATE_PLANNING}
    )
    public TarifaPlanejamentoDTO excluirTarifaPlanejamento(
            TarifaPlanejamentoDTO tarifaPlanejamento
    ) {
        deleteUC.delete(tarifaPlanejamento);
        return tarifaPlanejamento;
    }
}
