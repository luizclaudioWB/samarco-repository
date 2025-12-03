package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListFornecedorUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Fornecedor> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        QueryManager<Fornecedor> queryManager = new QueryManager<>( Fornecedor.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Fornecedor> queryManager ) {
        if ( filters == null ) return;

        var nome = (String) filters.get( "nome" );
        if ( nome != null && !nome.trim().isBlank() ) {
            queryManager.addLikeParam( "nome", "nome", nome );
        }

        var cnpj = (String) filters.get( "cnpj" );
        if ( cnpj != null && !cnpj.trim().isBlank() ) {
            queryManager.addLikeParam( "cnpj", "cnpj", cnpj );
        }

        var estado = (String) filters.get( "estado" );
        if ( estado != null && !estado.trim().isBlank() ) {
            queryManager.addEqualsParam( "estado", "estado", estado );
        }
    }
}
