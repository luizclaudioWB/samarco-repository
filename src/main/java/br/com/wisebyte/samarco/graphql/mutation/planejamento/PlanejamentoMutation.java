package br.com.wisebyte.samarco.graphql.mutation.planejamento;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.planejamento.CreatePlanejamentoUC;
import br.com.wisebyte.samarco.business.planejamento.DeletePlanejamentoUC;
import br.com.wisebyte.samarco.business.planejamento.UpdatePlanejamentoUC;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoMutation {

    @Inject
    CreatePlanejamentoUC cadastrarPlanejamento;

    @Inject
    UpdatePlanejamentoUC alterarPlanejamento;

    @Inject
    DeletePlanejamentoUC excluirPlanejamento;


    @Mutation(value = "cadastrarPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CADASTRAR_PLANEJAMENTO}
    )
    public PlanejamentoDTO cadastrarPlanejamento(PlanejamentoDTO dto) {
        return cadastrarPlanejamento.create(dto);
    }

    @Mutation(value = "alterarPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {ALTERAR_PLANEJAMENTO}
    )
    public PlanejamentoDTO alterarPlanejamento(PlanejamentoDTO dto) {
        return alterarPlanejamento.update(dto);
    }

    @Mutation(value = "excluirPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {EXCLUIR_PLANEJAMENTO}
    )
    public PlanejamentoDTO excluirPlanejamento(PlanejamentoDTO dto) {
        excluirPlanejamento.delete(dto);
        return dto;
    }
}
