package br.com.wisebyte.samarco.graphql.mutation.revisao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.revisao.CreateRevisaoUC;
import br.com.wisebyte.samarco.business.revisao.DeleteRevisaoUC;
import br.com.wisebyte.samarco.business.revisao.FinalizarRevisaoUC;
import br.com.wisebyte.samarco.business.revisao.UpdateRevisaoUC;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.dto.revisao.RevisaoInputDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class RevisaoMutation {

    @Inject
    CreateRevisaoUC cadastrarRevisao;

    @Inject
    UpdateRevisaoUC alterarRevisao;

    @Inject
    DeleteRevisaoUC excluirRevisao;

    @Inject
    FinalizarRevisaoUC finalizarRevisaoUC;

    @Mutation( value = "cadastrarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_REVISAO} )
    public RevisaoDTO cadastrarRevisao( RevisaoInputDTO dto ) {
        return cadastrarRevisao.create( dto );
    }

    @Mutation( value = "alterarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_REVISAO} )
    public RevisaoDTO alterarRevisao( RevisaoInputDTO dto ) {
        return alterarRevisao.update( dto );
    }

    @Mutation( value = "excluirRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_REVISAO} )
    public RevisaoDTO excluirRevisao( RevisaoDTO dto ) {
        excluirRevisao.delete( dto );
        return dto;
    }

    @Mutation( value = "finalizarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {FINALIZAR_REVISAO} )
    public RevisaoDTO finalizarRevisao( Long revisaoId ) {
        return finalizarRevisaoUC.finalizar( revisaoId );
    }
}
