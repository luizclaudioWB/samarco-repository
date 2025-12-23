package br.com.wisebyte.samarco.graphql.mutation.geracao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.geracao.CreatePlanejamentoGeracaoUC;
import br.com.wisebyte.samarco.business.geracao.DeletePlanejamentoGeracaoUC;
import br.com.wisebyte.samarco.business.geracao.UpdatePlanejamentoGeracaoUC;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoGeracaoMutation {

    @Inject
    CreatePlanejamentoGeracaoUC createUC;

    @Inject
    UpdatePlanejamentoGeracaoUC updateUC;

    @Inject
    DeletePlanejamentoGeracaoUC deleteUC;

    @Mutation("cadastrarPlanejamentoGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_GENERATION_PLANNING})
    public PlanejamentoGeracaoDTO cadastrarPlanejamentoGeracao(PlanejamentoGeracaoDTO planejamentoGeracao) {
        return createUC.create(planejamentoGeracao);
    }

    @Mutation("alterarPlanejamentoGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_GENERATION_PLANNING})
    public PlanejamentoGeracaoDTO alterarPlanejamentoGeracao(PlanejamentoGeracaoDTO planejamentoGeracao) {
        return updateUC.update(planejamentoGeracao);
    }

    @Mutation("excluirPlanejamentoGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_GENERATION_PLANNING})
    public PlanejamentoGeracaoDTO excluirPlanejamentoGeracao(PlanejamentoGeracaoDTO planejamentoGeracao) {
        deleteUC.delete(planejamentoGeracao);
        return planejamentoGeracao;
    }
}
