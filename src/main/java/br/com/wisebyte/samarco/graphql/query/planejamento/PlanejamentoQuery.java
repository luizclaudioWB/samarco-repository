package br.com.wisebyte.samarco.graphql.query.planejamento;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.planejamento.QueryPlanejamentoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_PLANNING_BY_YEAR;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_PLANNINGS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;


@GraphQLApi
@RequestScoped
public class PlanejamentoQuery {

    @Inject
    QueryPlanejamentoUC queryPlanejamentoUC;

    @Query( value = "planejamentos" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PLANNINGS} )
    public QueryList<PlanejamentoDTO> listarPlanejamentosPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryPlanejamentoUC.list( page, size );
    }

    @Query( value = "planejamentosPorAno" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PLANNING_BY_YEAR} )
    public PlanejamentoDTO buscarPlanejamentoPorAno( Integer ano ) {
        return queryPlanejamentoUC.findByAno( ano );
    }
}
