package br.com.wisebyte.samarco.graphql.query.geracao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.geracao.QueryPlanejamentoGeracaoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoGeracaoQuery {

    @Inject
    QueryPlanejamentoGeracaoUC queryUC;

    @Query("planejamentoGeracaoPorId")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_GENERATION_PLANNING_BY_ID})
    public PlanejamentoGeracaoDTO buscarPlanejamentoGeracaoPorId(@NotNull Long id) {
        return queryUC.findById(id);
    }

    @Query("planejamentosGeracaoPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_GENERATION_PLANNINGS})
    public QueryList<PlanejamentoGeracaoDTO> listarPlanejamentosGeracaoPorRevisao(@NotNull Long revisaoId) {
        return queryUC.findByRevisaoId(revisaoId);
    }
}
