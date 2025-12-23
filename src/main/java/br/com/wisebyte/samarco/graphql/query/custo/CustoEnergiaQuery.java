package br.com.wisebyte.samarco.graphql.query.custo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.custo.CalcCustoEnergiaUC;
import br.com.wisebyte.samarco.dto.custo.CustoEnergiaResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_ENERGY_COST;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class CustoEnergiaQuery {

    @Inject
    CalcCustoEnergiaUC calcCustoEnergiaUC;

    @Query("calcularCustoEnergia")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_ENERGY_COST})
    public CustoEnergiaResultDTO calcularCustoEnergia(@NotNull Long revisaoId) {
        return calcCustoEnergiaUC.calcularCustoEnergia(revisaoId);
    }
}
