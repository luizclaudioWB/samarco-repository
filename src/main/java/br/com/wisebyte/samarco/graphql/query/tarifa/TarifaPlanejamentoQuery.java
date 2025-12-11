package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.planejamento.QueryTarifaPlanejamentoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaPlanejamentoQuery {

    @Inject
    QueryTarifaPlanejamentoUC queryTarifaPlanejamentoUC;

    @Query( value = "tarifasPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_RATE_PLANNINGS} )
    public QueryList<TarifaPlanejamentoDTO> listarTarifasPlanejamento( @NotNull Integer page, @NotNull Integer size ) {
        return queryTarifaPlanejamentoUC.list( page, size );
    }

    @Query( value = "tarifaPlanejamentoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_RATE_PLANNING_BY_ID} )
    public TarifaPlanejamentoDTO buscarTarifaPlanejamentoPorId( @NotNull Long id ) {
        return queryTarifaPlanejamentoUC.findById( id );
    }

    @Query( value = "tarifaPlanejamentoPorRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_RATE_PLANNING_BY_REVISION} )
    public TarifaPlanejamentoDTO buscarTarifaPlanejamentoPorRevisao( @NotNull Long revisaoId ) {
        return queryTarifaPlanejamentoUC.findByRevisao( revisaoId );
    }
}
