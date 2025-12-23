package br.com.wisebyte.samarco.graphql.query.custo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.custo.CalcCustoClasseUC;
import br.com.wisebyte.samarco.dto.custo.CustoClasseResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_CUSTO_CLASSE;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class CustoClasseQuery {

    @Inject
    CalcCustoClasseUC calcCustoClasseUC;

    @Query("calcularCustoClasse")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_CUSTO_CLASSE})
    public CustoClasseResultDTO calcularCustoClasse(@NotNull Long revisaoId) {
        return calcCustoClasseUC.calcular(revisaoId);
    }
}
