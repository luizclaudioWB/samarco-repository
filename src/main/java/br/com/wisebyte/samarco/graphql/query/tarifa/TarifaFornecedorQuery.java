package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.fornecedor.QueryTarifaFornecedorUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_SUPPLIER_RATE_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_SUPPLIER_RATES;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaFornecedorQuery {

    @Inject
    QueryTarifaFornecedorUC queryTarifaFornecedorUC;

    @Query( value = "tarifasFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public QueryList<TarifaFornecedorDTO> listarTarifasFornecedor( @NotNull Integer page, @NotNull Integer size ) {
        return queryTarifaFornecedorUC.list( page, size );
    }

    @Query( value = "tarifaFornecedorPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SUPPLIER_RATE_BY_ID} )
    public TarifaFornecedorDTO buscarTarifaFornecedorPorId( Long id ) {
        return queryTarifaFornecedorUC.findById( id );
    }

    @Query( value = "tarifasFornecedorPorTarifaPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public QueryList<TarifaFornecedorDTO> listarTarifasFornecedorPorTarifaPlanejamento( @NotNull Long tarifaPlanejamentoId ) {
        return queryTarifaFornecedorUC.findByTarifaPlanejamento( tarifaPlanejamentoId );
    }

    @Query( value = "tarifasFornecedorPorFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public QueryList<TarifaFornecedorDTO> listarTarifasFornecedorPorFornecedor( @NotNull Long fornecedorId ) {
        return queryTarifaFornecedorUC.findByFornecedor( fornecedorId );
    }

}
