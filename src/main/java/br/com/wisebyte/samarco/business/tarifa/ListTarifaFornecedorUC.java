package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListTarifaFornecedorUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<TarifaFornecedor> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<TarifaFornecedor> queryManager = new QueryManager<>( TarifaFornecedor.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<TarifaFornecedor> queryManager ) {
        if ( filters == null ) return;

        var tarifaPlanejamentoId = filters.get( "tarifaPlanejamentoId" );
        if ( tarifaPlanejamentoId != null ) {
            Long id = tarifaPlanejamentoId instanceof Long
                    ? (Long) tarifaPlanejamentoId
                    : Long.parseLong( tarifaPlanejamentoId.toString() );
            queryManager.addEqualsParam( "planejamento.id", "tarifaPlanejamentoId", id );
        }

        var fornecedorId = filters.get( "fornecedorId" );
        if ( fornecedorId != null ) {
            Long id = fornecedorId instanceof Long
                    ? (Long) fornecedorId
                    : Long.parseLong( fornecedorId.toString() );
            queryManager.addEqualsParam( "fornecedor.id", "fornecedorId", id );
        }
    }
}
