package br.com.wisebyte.samarco.graphql.query.table.producao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producao.TabelaProducaoQueryUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.prducao.TablePlanejamentoProducaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.LIST_PRODUCTION_PLANNINGS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;
import static br.com.wisebyte.samarco.auth.Role.USER;

@GraphQLApi
@RequestScoped
public class TabelaProducaoQuery {

    @Inject
    TabelaProducaoQueryUC tabelaProducaoQueryUC;

    @Query( value = "tableProducaoQuery" )
    @SecuredAccess(
            roles = {ADMIN, USER},
            permissionsRequired = {LIST_PRODUCTION_PLANNINGS} )
    public QueryList<TablePlanejamentoProducaoDTO> calcComponentesTarifariosPorRevisaoDistribuidora( @NotNull Long revisaoId ) {
        return tabelaProducaoQueryUC.calcTabelaProducao( revisaoId );
    }
}
