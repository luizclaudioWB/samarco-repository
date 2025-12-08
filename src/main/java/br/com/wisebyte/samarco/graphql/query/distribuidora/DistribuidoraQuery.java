package br.com.wisebyte.samarco.graphql.query.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.QueryDistribuidoraUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_DISTRIBUTOR_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_DISTRIBUTORS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DistribuidoraQuery {


    @Inject
    QueryDistribuidoraUC queryDistribuidoraUC;

    @Query( value = "distribuidoras" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTORS} )
    public QueryList<DistribuidoraDTO> listarDistribuidorasPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryDistribuidoraUC.list( page, size );
    }


    @Query( value = "distribuidoraPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_DISTRIBUTOR_BY_ID} )
    public DistribuidoraDTO buscarDistribuidoraPorId( Long id ) {
        return queryDistribuidoraUC.findById( id );
    }
}
