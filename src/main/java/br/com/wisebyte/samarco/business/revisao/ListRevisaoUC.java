package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListRevisaoUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Revisao> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Revisao> queryManager = new QueryManager<>( Revisao.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Revisao> queryManager ) {
        if ( filters == null ) return;

        var descricao = (String) filters.get( "descricao" );
        if ( descricao != null && !descricao.trim().isBlank() ) {
            queryManager.addLikeParam( "descricao", "descricao", descricao );
        }

        var numeroRevisao = filters.get( "numeroRevisao" );
        if ( numeroRevisao != null ) {
            Integer numero = numeroRevisao instanceof Integer
                    ? (Integer) numeroRevisao
                    : Integer.parseInt( numeroRevisao.toString() );
            queryManager.addEqualsParam( "numeroRevisao", "numeroRevisao", numero );
        }

        var oficial = filters.get( "oficial" );
        if ( oficial != null ) {
            Boolean value = oficial instanceof Boolean ? (Boolean) oficial : Boolean.parseBoolean( oficial.toString() );
            queryManager.addEqualsParam( "oficial", "oficial", value );
        }

        var planejamentoId = filters.get( "planejamentoId" );
        if ( planejamentoId != null ) {
            Long id = planejamentoId instanceof Long ? (Long) planejamentoId : Long.parseLong( planejamentoId.toString() );
            queryManager.addEqualsParam( "planejamento.id", "planejamentoId", id );
        }
    }
}
