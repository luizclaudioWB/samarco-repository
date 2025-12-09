package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.QueryTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_DISTRIBUTOR_RATE_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_DISTRIBUTOR_RATES;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaDistribuidoraQuery {

    @Inject
    QueryTarifaDistribuidoraUC queryTarifaDistribuidoraUC;

    @Query( value = "tarifasDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<TarifaDistribuidoraDTO> listarTarifasDistribuidora( @NotNull Integer page, @NotNull Integer size ) {
        return queryTarifaDistribuidoraUC.list( page, size );
    }

    @Query( value = "tarifaDistribuidoraPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_DISTRIBUTOR_RATE_BY_ID} )
    public TarifaDistribuidoraDTO buscarTarifaDistribuidoraPorId( @NotNull Long id ) {
        return queryTarifaDistribuidoraUC.findById( id );
    }

    @Query( value = "tarifasDistribuidoraPorTarifaPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorTarifaPlanejamento( @NotNull Long tarifaPlanejamentoId ) {
        return queryTarifaDistribuidoraUC.findByTarifaPlanejamento( tarifaPlanejamentoId );
    }

    @Query( value = "tarifasDistribuidoraPorDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorDistribuidora( @NotNull Long distribuidoraId ) {
        return queryTarifaDistribuidoraUC.findByDistribuidora( distribuidoraId );
    }
}
