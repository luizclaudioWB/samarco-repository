package br.com.wisebyte.samarco.graphql.query.producao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producao.QueryPlanejamentoProducaoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_PRODUCTION_PLANNING_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_PRODUCTION_PLANNINGS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoProducaoQuery {

    @Inject
    QueryPlanejamentoProducaoUC queryPlanejamentoProducaoUC;

    @Query( "planejamentosProducao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PRODUCTION_PLANNINGS}
    )
    public QueryList<PlanejamentoProducaoDTO> listarPlanejamentosProducaoPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryPlanejamentoProducaoUC.list( page, size );
    }


    @Query( "planejamentoProducaoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PRODUCTION_PLANNING_BY_ID}
    )
    public PlanejamentoProducaoDTO buscarPlanejamentoProducaoPorId(Long id) {
        return queryPlanejamentoProducaoUC.findById( id );
    }


}