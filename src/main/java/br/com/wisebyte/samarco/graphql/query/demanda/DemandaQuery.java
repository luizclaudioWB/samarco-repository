package br.com.wisebyte.samarco.graphql.query.demanda;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.demanda.QueryDemandaUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DemandaQuery {

    @Inject
    QueryDemandaUC queryDemandaUC;

    @Query(value = "demandas")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_DEMANDS})
    public QueryList<DemandaDTO> listarDemandas(
        @NotNull Integer page,
        @NotNull Integer size
    ) {
        return queryDemandaUC.list(page, size);
    }

    @Query(value = "demandaPorId")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_DEMAND_BY_ID})
    public DemandaDTO buscarDemandaPorId(Long id) {
        return queryDemandaUC.findById(id);
    }

    @Query(value = "demandasPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_DEMANDS})
    public List<DemandaDTO> listarDemandasPorRevisao(Long revisaoId) {
        return queryDemandaUC.findByRevisao(revisaoId);
    }
}
