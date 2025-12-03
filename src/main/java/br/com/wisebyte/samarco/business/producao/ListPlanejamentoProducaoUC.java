package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListPlanejamentoProducaoUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<PlanejamentoProducao> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<PlanejamentoProducao> queryManager = new QueryManager<>( PlanejamentoProducao.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<PlanejamentoProducao> queryManager ) {
        if ( filters == null ) return;

        var revisaoId = filters.get( "revisaoId" );
        if ( revisaoId != null ) {
            Long id = revisaoId instanceof Long ? (Long) revisaoId : Long.parseLong( revisaoId.toString() );
            queryManager.addEqualsParam( "revisao.id", "revisaoId", id );
        }

        var areaId = filters.get( "areaId" );
        if ( areaId != null ) {
            Long id = areaId instanceof Long ? (Long) areaId : Long.parseLong( areaId.toString() );
            queryManager.addEqualsParam( "area.id", "areaId", id );
        }
    }
}
