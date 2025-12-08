package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.QueryAliquotaImpostosUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class AliquotaImpostosQuery {


    @Inject
    QueryAliquotaImpostosUC queryAliquotaImpostosUC;

    @Query( "aliquotasImpostos" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public QueryList<AliquotaImpostosDTO> listarAliquotasImpostosPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryAliquotaImpostosUC.list( page, size );
    }


    @Query( "aliquotasImpostosPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_TAX_RATE_BY_ID}
    )
    public AliquotaImpostosDTO buscarAliquotaImpostosPorId(Long id) {

    }


    @Query( "aliquotasImpostosPorEstado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES_BY_STATE}
    )
    public QueryList<AliquotaImpostosDTO> listarAliquotasPorEstado( Estado estado ) {

    }

    @Query( "aliquotasImpostosPorAno" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public QueryList<AliquotaImpostosDTO> listarAliquotasPorAno( Integer ano ) {

    }


    @Query( "aliquotasImpostosPorPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public QueryList<AliquotaImpostosDTO> listarAliquotasPorTarifaPlanejamento( Long tarifaPlanejamentoId ) {

    }
}
