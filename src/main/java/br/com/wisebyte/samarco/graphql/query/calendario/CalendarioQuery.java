package br.com.wisebyte.samarco.graphql.query.calendario;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.calendario.QueryCalendarioUC;
import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_CALENDARIO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class CalendarioQuery {

    @Inject
    QueryCalendarioUC queryUC;

    @Query("calendarioPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_CALENDARIO})
    public CalendarioDTO buscarCalendarioPorRevisao(@NotNull Long revisaoId) {
        return queryUC.findByRevisaoId(revisaoId);
    }
}
