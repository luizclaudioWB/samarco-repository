package br.com.wisebyte.samarco.graphql.mutation.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.CreateConsumoEspecificoUC;
import br.com.wisebyte.samarco.business.consumo.DeleteConsumoEspecificoUC;
import br.com.wisebyte.samarco.business.consumo.UpdateConsumoEspecificoUC;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
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
    CreateConsumoEspecificoUC createUC;

    @Inject
    UpdateConsumoEspecificoUC updateUC;

    @Inject
    DeleteConsumoEspecificoUC deleteUC;

    @Mutation("cadastrarConsumoEspecifico")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_SPECIFIC_CONSUMPTION}
    )
    public PlanejamentoConsumoEspecificoDTO cadastrarConsumoEspecifico(
            PlanejamentoConsumoEspecificoDTO consumoEspecifico
    ) {
        return createUC.create(consumoEspecifico);
    }

    @Mutation("alterarConsumoEspecifico")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_SPECIFIC_CONSUMPTION}
    )
    public PlanejamentoConsumoEspecificoDTO alterarConsumoEspecifico(
            PlanejamentoConsumoEspecificoDTO consumoEspecifico
    ) {
        return updateUC.update(consumoEspecifico);
    }

    @Mutation("excluirConsumoEspecifico")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_SPECIFIC_CONSUMPTION}
    )
    public PlanejamentoConsumoEspecificoDTO excluirConsumoEspecifico(
            PlanejamentoConsumoEspecificoDTO consumoEspecifico
    ) {
        deleteUC.delete(consumoEspecifico);
        return consumoEspecifico;
    }
}
