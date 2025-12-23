package br.com.wisebyte.samarco.graphql.mutation.calendario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.calendario.CreateCalendarioUC;
import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;

import static br.com.wisebyte.samarco.auth.Permissao.CREATE_CALENDARIO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class CalendarioMutation {

    @Inject
    CreateCalendarioUC createUC;

    @Mutation("criarCalendario")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CREATE_CALENDARIO})
    public CalendarioDTO criarCalendario(@Valid CalendarioDTO dto) {
        return createUC.execute(dto);
    }
}
