package br.com.wisebyte.samarco.graphql.query.geracao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.geracao.QueryGeracaoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class GeracaoQuery {

    @Inject
    QueryGeracaoUC queryGeracaoUC;

    @Query(value = "geracoes")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_GENERATIONS})
    public QueryList<GeracaoDTO> listarGeracoes(
        @NotNull Integer page,
        @NotNull Integer size
    ) {
        return queryGeracaoUC.list(page, size);
    }

    @Query(value = "geracaoPorId")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_GENERATION_BY_ID})
    public GeracaoDTO buscarGeracaoPorId(Long id) {
        return queryGeracaoUC.findById(id);
    }

    @Query(value = "geracoesPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_GENERATIONS})
    public List<GeracaoDTO> listarGeracoesPorRevisao(Long revisaoId) {
        return queryGeracaoUC.findByRevisao(revisaoId);
    }

    @Query(value = "geracoesPorUnidade")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_GENERATIONS})
    public List<GeracaoDTO> listarGeracoesPorUnidade(Long unidadeId) {
        return queryGeracaoUC.findByUnidade(unidadeId);
    }
}
