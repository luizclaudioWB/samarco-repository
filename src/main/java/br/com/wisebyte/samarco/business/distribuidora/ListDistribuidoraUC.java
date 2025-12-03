package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListDistribuidoraUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Distribuidora> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Distribuidora> queryManager = new QueryManager<>( Distribuidora.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Distribuidora> queryManager ) {
        if ( filters == null ) return;

        var nome = (String) filters.get( "nome" );
        if ( nome != null && !nome.trim().isBlank() ) {
            queryManager.addLikeParam( "nome", "nome", nome );
        }

        var cnpj = (String) filters.get( "cnpj" );
        if ( cnpj != null && !cnpj.trim().isBlank() ) {
            queryManager.addLikeParam( "cnpj", "cnpj", cnpj );
        }

        var siglaAgente = (String) filters.get( "siglaAgente" );
        if ( siglaAgente != null && !siglaAgente.trim().isBlank() ) {
            queryManager.addLikeParam( "siglaAgente", "siglaAgente", siglaAgente );
        }

        var estado = (String) filters.get( "estado" );
        if ( estado != null && !estado.trim().isBlank() ) {
            queryManager.addEqualsParam( "estado", "estado", estado );
        }
    }
}
