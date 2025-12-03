package br.com.wisebyte.samarco.graphql.mutation.producao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producao.*;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoProducaoMutation {

    @Inject
    CreatePlanejamentoProducaoUC createUC;

    @Inject
    UpdatePlanejamentoProducaoUC updateUC;

    @Inject
    DeletePlanejamentoProducaoUC deleteUC;

    @Mutation("cadastrarPlanejamentoProducao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_PRODUCTION_PLANNING}
    )
    public PlanejamentoProducaoDTO cadastrarPlanejamentoProducao(
            PlanejamentoProducaoDTO planejamentoProducao
    ) {
        return createUC.create(planejamentoProducao);
    }

    @Mutation("alterarPlanejamentoProducao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_PRODUCTION_PLANNING}
    )
    public PlanejamentoProducaoDTO alterarPlanejamentoProducao(
            PlanejamentoProducaoDTO planejamentoProducao
    ) {
        return updateUC.update(planejamentoProducao);
    }

    @Mutation("excluirPlanejamentoProducao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_PRODUCTION_PLANNING}
    )
    public PlanejamentoProducaoDTO excluirPlanejamentoProducao(
            PlanejamentoProducaoDTO planejamentoProducao
    ) {
        deleteUC.delete(planejamentoProducao);
        return planejamentoProducao;
    }
}