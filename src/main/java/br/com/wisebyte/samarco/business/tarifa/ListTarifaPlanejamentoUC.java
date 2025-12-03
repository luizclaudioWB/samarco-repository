package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListTarifaPlanejamentoUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<TarifaPlanejamento> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<TarifaPlanejamento> queryManager = new QueryManager<>( TarifaPlanejamento.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<TarifaPlanejamento> queryManager ) {
        if ( filters == null ) return;

        var revisaoId = filters.get( "revisaoId" );
        if ( revisaoId != null ) {
            Long id = revisaoId instanceof Long ? (Long) revisaoId : Long.parseLong( revisaoId.toString() );
            queryManager.addEqualsParam( "revisao.id", "revisaoId", id );
        }
    }
}
