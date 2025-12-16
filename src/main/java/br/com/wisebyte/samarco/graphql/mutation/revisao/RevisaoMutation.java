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
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

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

    @Inject
    @Claim( standard = Claims.preferred_username )
    String username;

    @Inject
    @ConfigProperty( name = "quarkus.profile" )
    String activeProfile;

    @Mutation( value = "cadastrarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_REVISION} )
    public RevisaoDTO cadastrarRevisao( RevisaoInputDTO dto ) {
        return cadastrarRevisao.create( dto );
    }

    @Mutation( value = "alterarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_REVISION} )
    public RevisaoDTO alterarRevisao( RevisaoInputDTO dto ) {
        return alterarRevisao.update( dto );
    }

    @Mutation( value = "excluirRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_REVISION} )
    public RevisaoDTO excluirRevisao( RevisaoDTO dto ) {
        excluirRevisao.delete( dto );
        return dto;
    }

    @Mutation( value = "finalizarRevisao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {FINALIZE_REVISION} )
    public RevisaoDTO finalizarRevisao( Long revisaoId ) {
        String user = activeProfile.equals( "dev" ) ? "leandro@wisebyte.com.br" : username;
        return finalizarRevisaoUC.finalizar( revisaoId, user );
    }
}
