package br.com.wisebyte.samarco.graphql.query.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.QueryConsumoEspecificoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ConsumoEspecificoQuery {

    @Inject
    QueryConsumoEspecificoUC queryConsumoEspecificoUC;

    @Query(value = "consumoEspecificoPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SPECIFIC_CONSUMPTION_BY_ID})
    public PlanejamentoConsumoEspecificoDTO buscarConsumoEspecificoPorId(@NotNull Long id) {
        return queryConsumoEspecificoUC.findById(id);
    }

    @Query(value = "consumosEspecificosPorRevisao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SPECIFIC_CONSUMPTIONS})
    public QueryList<PlanejamentoConsumoEspecificoDTO> listarConsumoEspecificoPorRevisao(@NotNull Long revisaoId) {
        return queryConsumoEspecificoUC.findByRevisaoId(revisaoId);
    }

    @Query(value = "consumoEspecificoPorRevisaoEArea")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SPECIFIC_CONSUMPTION_BY_ID})
    public PlanejamentoConsumoEspecificoDTO buscarConsumoEspecificoPorRevisaoEArea(
            @NotNull Long revisaoId,
            @NotNull Long areaId) {
        return queryConsumoEspecificoUC.findByRevisaoIdAndAreaId(revisaoId, areaId);
    }
}
