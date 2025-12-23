package br.com.wisebyte.samarco.graphql.query.encargo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.encargo.QueryEncargosSetoriaisUC;
import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_ENCARGOS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class EncargosSetoriaisQuery {

    @Inject
    QueryEncargosSetoriaisUC queryUC;

    @Query("encargosSetoriaisPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_ENCARGOS})
    public EncargosSetoriaisDTO buscarEncargosSetoriaisPorRevisao(@NotNull Long revisaoId) {
        return queryUC.findByRevisaoId(revisaoId);
    }
}
