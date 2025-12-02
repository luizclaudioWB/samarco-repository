package br.com.wisebyte.samarco.graphql.mutation.fornecedor;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.fornecedor.CreateFornecedorUC;
import br.com.wisebyte.samarco.business.fornecedor.DeleteFornecedorUC;
import br.com.wisebyte.samarco.business.fornecedor.UpdateFornecedorUC;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class FornecedorMutation {

    @Inject
    CreateFornecedorUC cadastrarFornecedor;

    @Inject
    UpdateFornecedorUC alterarFornecedor;

    @Inject
    DeleteFornecedorUC excluirFornecedor;

    @Mutation( value = "cadastrarFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_SUPPLIER} )
    public FornecedorDTO cadastrarFornecedor( FornecedorDTO dto ) {
        return cadastrarFornecedor.create( dto );
    }

    @Mutation( value = "alterarFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_SUPPLIER} )
    public FornecedorDTO alterarFornecedor( FornecedorDTO dto ) {
        return alterarFornecedor.update( dto );
    }

    @Mutation( value = "excluirFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_SUPPLIER} )
    public FornecedorDTO excluirFornecedor( FornecedorDTO dto ) {
        excluirFornecedor.delete( dto );
        return dto;
    }
}
