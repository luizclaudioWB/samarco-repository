package br.com.wisebyte.samarco.graphql.query.custo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.custo.CalcCustoCentroUC;
import br.com.wisebyte.samarco.dto.custo.CustoCentroResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_CUSTO_CENTRO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class CustoCentroQuery {

    @Inject
    CalcCustoCentroUC calcCustoCentroUC;

    @Query("calcularCustoCentro")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_CUSTO_CENTRO})
    public CustoCentroResultDTO calcularCustoCentro(@NotNull Long revisaoId) {
        return calcCustoCentroUC.calcular(revisaoId);
    }
}
