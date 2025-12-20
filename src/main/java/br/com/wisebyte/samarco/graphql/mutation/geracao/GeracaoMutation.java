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

    @Mutation(value = "cadastrarGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_GENERATION})
    public GeracaoDTO cadastrarGeracao(GeracaoDTO dto) {
        return createGeracaoUC.create(dto);
    }

    @Mutation(value = "alterarGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {UPDATE_GENERATION})
    public GeracaoDTO alterarGeracao(GeracaoDTO dto) {
        return updateGeracaoUC.update(dto);
    }

    @Mutation(value = "excluirGeracao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {DELETE_GENERATION})
    public GeracaoDTO excluirGeracao(GeracaoDTO dto) {
        deleteGeracaoUC.delete(dto);
        return dto;
    }
}
