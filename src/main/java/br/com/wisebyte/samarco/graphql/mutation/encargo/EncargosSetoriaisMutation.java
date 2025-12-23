package br.com.wisebyte.samarco.graphql.mutation.encargo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.encargo.CreateEncargosSetoriaisUC;
import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.CREATE_ENCARGOS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class EncargosSetoriaisMutation {

    @Inject
    CreateEncargosSetoriaisUC createUC;

    @Mutation("criarEncargosSetoriais")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_ENCARGOS})
    public EncargosSetoriaisDTO criarEncargosSetoriais(@Valid EncargosSetoriaisDTO dto) {
        return createUC.execute(dto);
    }
}
