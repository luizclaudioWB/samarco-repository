package br.com.wisebyte.samarco.graphql.mutation.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.CadastrarDistribuidoraUC;
import br.com.wisebyte.samarco.business.unidade.AlterarUnidadeUC;
import br.com.wisebyte.samarco.business.unidade.ExcluirUnidadeUC;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
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
    AlterarUnidadeUC alterarUnidade;

    @Inject
    ExcluirUnidadeUC excluirUnidade;

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
    public UnidadeDTO alterarUnidade( UnidadeDTO unidade ) {
        return alterarUnidade.alterar( unidade );
    }

    @Mutation( value = "excluirDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_DISTRIBUIDORA} )
    public UnidadeDTO excluirUnidade( UnidadeDTO unidade ) {
        excluirUnidade.excluir( unidade );
        return unidade;
    }
}
