package br.com.wisebyte.samarco.graphql.mutation.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.CreateTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.business.tarifa.DeleteTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.business.tarifa.UpdateTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaDistribuidoraMutation {

    @Inject
    CreateTarifaDistribuidoraUC createUC;

    @Inject
    UpdateTarifaDistribuidoraUC updateUC;

    @Inject
    DeleteTarifaDistribuidoraUC deleteUC;

    @Mutation("cadastrarTarifaDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_DISTRIBUTOR_RATE}
    )
    public TarifaDistribuidoraDTO cadastrarTarifaDistribuidora(
            TarifaDistribuidoraDTO tarifaDistribuidora
    ) {
        return createUC.create(tarifaDistribuidora);
    }

    @Mutation("alterarTarifaDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_DISTRIBUTOR_RATE}
    )
    public TarifaDistribuidoraDTO alterarTarifaDistribuidora(
            TarifaDistribuidoraDTO tarifaDistribuidora
    ) {
        return updateUC.update(tarifaDistribuidora);
    }

    @Mutation("excluirTarifaDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_DISTRIBUTOR_RATE}
    )
    public TarifaDistribuidoraDTO excluirTarifaDistribuidora(
            TarifaDistribuidoraDTO tarifaDistribuidora
    ) {
        deleteUC.delete(tarifaDistribuidora);
        return tarifaDistribuidora;
    }
}
