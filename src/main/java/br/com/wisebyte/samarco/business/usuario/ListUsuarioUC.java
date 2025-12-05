package br.com.wisebyte.samarco.business.usuario;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import br.com.wisebyte.samarco.core.query.QueryManager;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Map;

@ApplicationScoped
public class ListUsuarioUC {

    @Inject
    EntityManager entityManager;

    public GraphQLQueryList<Usuario> list(
            Map<String, Object> filters,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size ) {
        QueryManager<Usuario> queryManager = new QueryManager<>( Usuario.class );
        buildParams( filters, queryManager );
        return queryManager.buildQuery( entityManager, sortField, sortDirection, page, size );
    }

    private void buildParams( Map<String, Object> filters, QueryManager<Usuario> queryManager ) {
        if ( filters == null ) return;

        var nome = (String) filters.get( "nome" );
        if ( nome != null && !nome.trim().isBlank() ) {
            queryManager.addLikeParam( "nome", "nome", nome );
        }

        var usuario = (String) filters.get( "usuario" );
        if ( usuario != null && !usuario.trim().isBlank() ) {
            queryManager.addLikeParam( "usuario", "usuario", usuario );
        }
    }
}
