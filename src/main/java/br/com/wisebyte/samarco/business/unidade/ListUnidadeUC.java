package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListUnidadeUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Unidade> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Unidade> queryManager = new QueryManager<>( Unidade.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Unidade> queryManager ) {
        if ( filters == null ) return;

        var nome = (String) filters.get( "nome" );
        if ( nome != null && !nome.trim().isBlank() ) {
            queryManager.addLikeParam( "nomeUnidade", "nome", nome );
        }

        var estado = (String) filters.get( "estado" );
        if ( estado != null && !estado.trim().isBlank() ) {
            queryManager.addEqualsParam( "estado", "estado", estado );
        }

        var geraEnergia = filters.get( "geraEnergia" );
        if ( geraEnergia != null ) {
            Boolean value = geraEnergia instanceof Boolean ? (Boolean) geraEnergia : Boolean.parseBoolean( geraEnergia.toString() );
            queryManager.addEqualsParam( "unidadeGeradora", "unidadeGeradora", value );
        }

        var conectadaRedeBasica = filters.get( "conectadaRedeBasica" );
        if ( conectadaRedeBasica != null ) {
            Boolean value = conectadaRedeBasica instanceof Boolean ? (Boolean) conectadaRedeBasica : Boolean.parseBoolean( conectadaRedeBasica.toString() );
            queryManager.addEqualsParam( "conectadaRedeBasica", "conectadaRedeBasica", value );
        }
    }
}
