package br.com.wisebyte.samarco.graphql.query.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.CalcConsumoAreaUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_CONSUMPTION_AREA;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ConsumoAreaQuery {

    @Inject
    CalcConsumoAreaUC calcConsumoAreaUC;

    @Query(value = "calcularConsumoArea")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {CALC_CONSUMPTION_AREA})
    public QueryList<ConsumoAreaResultDTO> calcularConsumoArea(@NotNull Long revisaoId) {
        return calcConsumoAreaUC.calcConsumoArea(revisaoId);
    }
}
