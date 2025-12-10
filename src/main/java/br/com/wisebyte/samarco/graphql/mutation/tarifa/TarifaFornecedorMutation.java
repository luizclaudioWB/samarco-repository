package br.com.wisebyte.samarco.graphql.mutation.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.fornecedor.CreateTarifaFornecedorUC;
import br.com.wisebyte.samarco.business.tarifa.fornecedor.DeleteTarifaFornecedorUC;
import br.com.wisebyte.samarco.business.tarifa.fornecedor.UpdateTarifaFornecedorUC;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaFornecedorMutation {

    @Inject
    CreateTarifaFornecedorUC createUC;

    @Inject
    UpdateTarifaFornecedorUC updateUC;

    @Inject
    DeleteTarifaFornecedorUC deleteUC;

    @Mutation("cadastrarTarifaFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_SUPPLIER_RATE}
    )
    public TarifaFornecedorDTO cadastrarTarifaFornecedor(
            TarifaFornecedorDTO tarifaFornecedor
    ) {
        return createUC.create(tarifaFornecedor);
    }

    @Mutation("alterarTarifaFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_SUPPLIER_RATE}
    )
    public TarifaFornecedorDTO alterarTarifaFornecedor(
            TarifaFornecedorDTO tarifaFornecedor
    ) {
        return updateUC.update(tarifaFornecedor);
    }

    @Mutation("excluirTarifaFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_SUPPLIER_RATE}
    )
    public TarifaFornecedorDTO excluirTarifaFornecedor(
            TarifaFornecedorDTO tarifaFornecedor
    ) {
        deleteUC.delete(tarifaFornecedor);
        return tarifaFornecedor;
    }
}
