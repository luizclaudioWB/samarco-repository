package br.com.wisebyte.samarco.graphql.query.unidade;

import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RequestScoped
public class UnidadeQuery {

    @Query
    public String health( ) {
        return "OK";
    }
}
