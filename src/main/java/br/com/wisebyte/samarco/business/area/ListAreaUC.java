package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.area.TipoArea;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListAreaUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Area> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Area> queryManager = new QueryManager<>( Area.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Area> queryManager ) {
        if ( filters == null ) return;

        var nome = (String) filters.get( "nome" );
        if ( nome != null && !nome.trim().isBlank() ) {
            queryManager.addLikeParam( "nomeArea", "nome", nome );
        }

        var tipoArea = filters.get( "tipoArea" );
        if ( tipoArea != null ) {
            TipoArea tipo = tipoArea instanceof TipoArea
                    ? (TipoArea) tipoArea
                    : TipoArea.valueOf( tipoArea.toString() );
            queryManager.addEqualsParam( "tipoArea", "tipoArea", tipo );
        }

        var ativo = filters.get( "ativo" );
        if ( ativo != null ) {
            Boolean value = ativo instanceof Boolean ? (Boolean) ativo : Boolean.parseBoolean( ativo.toString() );
            queryManager.addEqualsParam( "ativo", "ativo", value );
        }

        var unidadeId = filters.get( "unidadeId" );
        if ( unidadeId != null ) {
            Long id = unidadeId instanceof Long ? (Long) unidadeId : Long.parseLong( unidadeId.toString() );
            queryManager.addEqualsParam( "unidade.id", "unidadeId", id );
        }
    }
}
