package br.com.wisebyte.samarco.graphql.query.table.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.ComponenteTarifarioDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.LIST_DISTRIBUTOR_RATES;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TabelaTarifaDistribuidorasQuery {


    @Query( value = "calcTabelaTarifaDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<ComponenteTarifarioDTO> calcComponentesTarifariosPorRevisaoDistribuidora( @NotNull Long revisaoId ) {
        return null;
    }
}
