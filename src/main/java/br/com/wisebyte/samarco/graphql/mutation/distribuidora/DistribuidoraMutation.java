package br.com.wisebyte.samarco.graphql.mutation.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.CreateDistribuidoraUC;
import br.com.wisebyte.samarco.business.distribuidora.DeleteDistribuidoraUC;
import br.com.wisebyte.samarco.business.distribuidora.UpdateDistribuidoraUC;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DistribuidoraMutation {

    @Inject
    CreateDistribuidoraUC cadastrarDistribuidora;

    @Inject
    UpdateDistribuidoraUC alterarDistribuidora;

    @Inject
    DeleteDistribuidoraUC excluirDistribuidora;

    @Mutation( value = "cadastrarDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_DISTRIBUIDORA} )
    public DistribuidoraDTO cadastrarDistribuidora( DistribuidoraDTO dto ) {
        return cadastrarDistribuidora.create( dto );
    }

    @Mutation( value = "alterarDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_DISTRIBUIDORA} )
    public DistribuidoraDTO alterarUnidade( DistribuidoraDTO dto ) {
        return alterarDistribuidora.update( dto );
    }

    @Mutation( value = "excluirDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_DISTRIBUIDORA} )
    public DistribuidoraDTO excluirUnidade( DistribuidoraDTO dto ) {
        excluirDistribuidora.delete( dto );
        return dto;
    }
}
