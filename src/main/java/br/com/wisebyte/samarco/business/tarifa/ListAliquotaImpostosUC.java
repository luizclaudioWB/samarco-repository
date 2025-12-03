package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListAliquotaImpostosUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<AliquotaImpostos> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<AliquotaImpostos> queryManager = new QueryManager<>( AliquotaImpostos.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<AliquotaImpostos> queryManager ) {
        if ( filters == null ) return;

        var ano = filters.get( "ano" );
        if ( ano != null ) {
            Integer anoValue = ano instanceof Integer ? (Integer) ano : Integer.parseInt( ano.toString() );
            queryManager.addEqualsParam( "ano", "ano", anoValue );
        }

        var estado = filters.get( "estado" );
        if ( estado != null ) {
            Estado estadoValue = estado instanceof Estado
                    ? (Estado) estado
                    : Estado.valueOf( estado.toString() );
            queryManager.addEqualsParam( "estado", "estado", estadoValue );
        }

        var tarifaPlanejamentoId = filters.get( "tarifaPlanejamentoId" );
        if ( tarifaPlanejamentoId != null ) {
            Long id = tarifaPlanejamentoId instanceof Long
                    ? (Long) tarifaPlanejamentoId
                    : Long.parseLong( tarifaPlanejamentoId.toString() );
            queryManager.addEqualsParam( "planejamento.id", "tarifaPlanejamentoId", id );
        }
    }
}
