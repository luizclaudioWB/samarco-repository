package br.com.wisebyte.samarco.graphql.query.custo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.custo.CalcDistribuicaoCargaUC;
import br.com.wisebyte.samarco.dto.custo.DistribuicaoCargaResultDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.CALC_DISTRIBUICAO_CARGA;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DistribuicaoCargaQuery {

    @Inject
    CalcDistribuicaoCargaUC calcDistribuicaoCargaUC;

    @Query("calcularDistribuicaoCarga")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_DISTRIBUICAO_CARGA})
    public DistribuicaoCargaResultDTO calcularDistribuicaoCarga(@NotNull Long revisaoId) {
        return calcDistribuicaoCargaUC.calcular(revisaoId);
    }
}
