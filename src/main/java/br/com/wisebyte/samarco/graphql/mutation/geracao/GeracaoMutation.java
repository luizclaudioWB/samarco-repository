package br.com.wisebyte.samarco.graphql.mutation.geracao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.geracao.CreateGeracaoUC;
import br.com.wisebyte.samarco.business.geracao.DeleteGeracaoUC;
import br.com.wisebyte.samarco.business.geracao.UpdateGeracaoUC;
import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class GeracaoMutation {

    @Inject
    CreateGeracaoUC createGeracaoUC;

    @Inject
    UpdateGeracaoUC updateGeracaoUC;

    @Inject
    DeleteGeracaoUC deleteGeracaoUC;

    @Mutation(value = "criarGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_GENERATION})
    public GeracaoDTO criarGeracao(GeracaoDTO input) {
        return createGeracaoUC.execute(input);
    }

    @Mutation(value = "atualizarGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_GENERATION})
    public GeracaoDTO atualizarGeracao(GeracaoDTO input) {
        return updateGeracaoUC.execute(input);
    }

    @Mutation(value = "deletarGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_GENERATION})
    public Boolean deletarGeracao(Long id) {
        deleteGeracaoUC.execute(id);
        return true;
    }
}
