package br.com.wisebyte.samarco.graphql.query.custo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.custo.CalcResumoGeralUC;
import br.com.wisebyte.samarco.dto.custo.ResumoGeralResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_RESUMO_GERAL;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ResumoGeralQuery {

    @Inject
    CalcResumoGeralUC calcResumoGeralUC;

    @Query("calcularResumoGeral")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_RESUMO_GERAL})
    public ResumoGeralResultDTO calcularResumoGeral(@NotNull Long revisaoId) {
        return calcResumoGeralUC.calcular(revisaoId);
    }
}
