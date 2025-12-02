package br.com.wisebyte.samarco.graphql.mutation.planejamento;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.planejamento.CreatePlanejamentoUC;
import br.com.wisebyte.samarco.business.planejamento.DeletePlanejamentoUC;
import br.com.wisebyte.samarco.business.planejamento.UpdatePlanejamentoUC;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
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
public class PlanejamentoMutation {

    @Inject
    CreatePlanejamentoUC cadastrarPlanejamento;

    @Inject
    UpdatePlanejamentoUC alterarPlanejamento;

    @Inject
    DeletePlanejamentoUC excluirPlanejamento;

    @Inject
    @Claim( standard = Claims.preferred_username )
    String username;

    @Inject
    @ConfigProperty(name = "quarkus.profile")
    String activeProfile;

    @Mutation(value = "cadastrarPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CREATE_PLANNING}
    )
    public PlanejamentoDTO cadastrarPlanejamento(PlanejamentoDTO dto) {
        String user = activeProfile.equals("dev") ? "leandro.samarco@gmail.com" : username;
        return cadastrarPlanejamento.create(dto, user);
    }

    @Mutation(value = "alterarPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {UPDATE_PLANNING}
    )
    public PlanejamentoDTO alterarPlanejamento(PlanejamentoDTO dto) {
        return alterarPlanejamento.update(dto);
    }

    @Mutation(value = "excluirPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {DELETE_PLANNING}
    )
    public PlanejamentoDTO excluirPlanejamento(PlanejamentoDTO dto) {
        excluirPlanejamento.delete(dto);
        return dto;
    }
}
