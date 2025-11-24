package br.com.wisebyte.samarco.graphql.mutation.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.AlterarDistribuidoraUC;
import br.com.wisebyte.samarco.business.distribuidora.CadastrarDistribuidoraUC;
import br.com.wisebyte.samarco.business.distribuidora.ExcluirDistribuidoraUC;
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
    CadastrarDistribuidoraUC cadastrarDistribuidora;

    @Inject
    AlterarDistribuidoraUC alterarDistribuidora;

    @Inject
    ExcluirDistribuidoraUC excluirDistribuidora;

    @Mutation( value = "cadastrarDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_DISTRIBUIDORA} )
    public DistribuidoraDTO cadastrarDistribuidora( DistribuidoraDTO dto ) {
        return cadastrarDistribuidora.cadastrar( dto );
    }

    @Mutation( value = "alterarDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_DISTRIBUIDORA} )
    public DistribuidoraDTO alterarUnidade( DistribuidoraDTO dto ) {
        return alterarDistribuidora.alterar( dto );
    }

    @Mutation( value = "excluirDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_DISTRIBUIDORA} )
    public DistribuidoraDTO excluirUnidade( DistribuidoraDTO dto ) {
        excluirDistribuidora.excluir( dto );
        return dto;
    }
}
