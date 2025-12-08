package br.com.wisebyte.samarco.graphql.query.fornecedor;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.fornecedor.QueryFornecedorUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_SUPPLIER_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_SUPPLIERS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class FornecedorQuery {

    @Inject
    QueryFornecedorUC queryFornecedorUC;

    @Query( value = "fornecedores" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIERS} )
    public QueryList<FornecedorDTO> listarFornecedoresPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryFornecedorUC.list( page, size );
    }

    @Query( value = "fornecedorPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SUPPLIER_BY_ID} )
    public FornecedorDTO buscarFornecedorPorId( Long id ) {
        return queryFornecedorUC.findById( id );
    }
}
