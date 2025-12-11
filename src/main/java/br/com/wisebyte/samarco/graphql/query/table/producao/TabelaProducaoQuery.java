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

import static br.com.wisebyte.samarco.auth.Permissao.LIST_DISTRIBUTOR_RATES;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TabelaProducaoQuery {

    @Inject
    TabelaProducaoQueryUC tabelaProducaoQueryUC;

    @Query( value = "tableProducaoQuery" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<TablePlanejamentoProducaoDTO> calcComponentesTarifariosPorRevisaoDistribuidora( @NotNull Long revisaoId ) {
        return tabelaProducaoQueryUC.calcTabelaProducao( revisaoId );
    }
}
