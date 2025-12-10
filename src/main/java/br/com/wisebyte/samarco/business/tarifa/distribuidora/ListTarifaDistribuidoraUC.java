package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListTarifaDistribuidoraUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<TarifaDistribuidora> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<TarifaDistribuidora> queryManager = new QueryManager<>( TarifaDistribuidora.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<TarifaDistribuidora> queryManager ) {
        if ( filters == null ) return;

        var tarifaPlanejamentoId = filters.get( "tarifaPlanejamentoId" );
        if ( tarifaPlanejamentoId != null ) {
            Long id = tarifaPlanejamentoId instanceof Long
                    ? (Long) tarifaPlanejamentoId
                    : Long.parseLong( tarifaPlanejamentoId.toString() );
            queryManager.addEqualsParam( "planejamento.id", "tarifaPlanejamentoId", id );
        }

        var distribuidoraId = filters.get( "distribuidoraId" );
        if ( distribuidoraId != null ) {
            Long id = distribuidoraId instanceof Long
                    ? (Long) distribuidoraId
                    : Long.parseLong( distribuidoraId.toString() );
            queryManager.addEqualsParam( "distribuidora.id", "distribuidoraId", id );
        }
    }
}
