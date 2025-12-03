package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListPlanejamentoUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Planejamento> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Planejamento> queryManager = new QueryManager<>( Planejamento.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Planejamento> queryManager ) {
        if ( filters == null ) return;

        var ano = filters.get( "ano" );
        if ( ano != null ) {
            Integer value = ano instanceof Integer ? (Integer) ano : Integer.parseInt( ano.toString() );
            queryManager.addEqualsParam( "ano", "ano", value );
        }

        var descricao = (String) filters.get( "descricao" );
        if ( descricao != null && !descricao.trim().isBlank() ) {
            queryManager.addLikeParam( "descricao", "descricao", descricao );
        }

        var corrente = filters.get( "corrente" );
        if ( corrente != null ) {
            Boolean value = corrente instanceof Boolean ? (Boolean) corrente : Boolean.parseBoolean( corrente.toString() );
            queryManager.addEqualsParam( "corrente", "corrente", value );
        }
    }
}
