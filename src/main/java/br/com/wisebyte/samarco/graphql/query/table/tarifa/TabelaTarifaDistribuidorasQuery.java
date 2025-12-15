package br.com.wisebyte.samarco.graphql.query.table.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.TabelaDistribuidoraQueryUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.distribuidora.TabelaDistribuidoraLineDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

import static br.com.wisebyte.samarco.auth.Permissao.LIST_DISTRIBUTOR_TABLE;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;
import static br.com.wisebyte.samarco.auth.Role.USER;

@GraphQLApi
@RequestScoped
public class TabelaTarifaDistribuidorasQuery {

    @Inject
    TabelaDistribuidoraQueryUC tabelaDistribuidoraQueryUC;

    @Query( value = "tableTarifasDistribuidoras" )
    @SecuredAccess(
            roles = {ADMIN, USER},
            permissionsRequired = {LIST_DISTRIBUTOR_TABLE} )
    public QueryList<TabelaDistribuidoraLineDTO> calcComponentesTarifariosPorRevisaoDistribuidora( @NotNull Long revisaoId, @NotNull Long unidadeId ) {
        List<TabelaDistribuidoraLineDTO> tabelaDistribuidoraLineDTOS = tabelaDistribuidoraQueryUC.calcTabelaDistribuidora( revisaoId, unidadeId );
        return QueryList.<TabelaDistribuidoraLineDTO>builder( )
                .totalElements( ( long ) tabelaDistribuidoraLineDTOS.size( ) )
                .totalPages( 1L )
                .results( tabelaDistribuidoraLineDTOS )
                .build( );
    }
}
