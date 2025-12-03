package br.com.wisebyte.samarco.graphql.mutation.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.CreateAliquotaImpostosUC;
import br.com.wisebyte.samarco.business.tarifa.DeleteAliquotaImpostosUC;
import br.com.wisebyte.samarco.business.tarifa.UpdateAliquotaImpostosUC;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class AliquotaImpostosMutation {

    @Inject
    CreateAliquotaImpostosUC createUC;

    @Inject
    UpdateAliquotaImpostosUC updateUC;

    @Inject
    DeleteAliquotaImpostosUC deleteUC;

    @Mutation("cadastrarAliquotaImpostos")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_TAX_RATE}
    )
    public AliquotaImpostosDTO cadastrarAliquotaImpostos(
            AliquotaImpostosDTO aliquotaImpostos
    ) {
        return createUC.create(aliquotaImpostos);
    }


    @Mutation("alterarAliquotaImpostos")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_TAX_RATE}
    )
    public AliquotaImpostosDTO alterarAliquotaImpostos(
            AliquotaImpostosDTO aliquotaImpostos
    ) {
        return updateUC.update(aliquotaImpostos);
    }


    @Mutation("excluirAliquotaImpostos")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_TAX_RATE}
    )
    public AliquotaImpostosDTO excluirAliquotaImpostos(
            AliquotaImpostosDTO aliquotaImpostos
    ) {
        deleteUC.delete(aliquotaImpostos);
        return aliquotaImpostos;
    }
}
