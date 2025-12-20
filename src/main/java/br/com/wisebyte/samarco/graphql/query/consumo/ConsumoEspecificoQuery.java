package br.com.wisebyte.samarco.graphql.query.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.CalcConsumoAreaUC;
import br.com.wisebyte.samarco.business.consumo.QueryConsumoEspecificoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
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
public class ConsumoEspecificoQuery {

    @Inject
    QueryConsumoEspecificoUC queryConsumoUC;

    @Inject
    CalcConsumoAreaUC calcConsumoAreaUC;

    @Query(value = "consumosEspecificos")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_SPECIFIC_CONSUMPTIONS})
    public QueryList<ConsumoEspecificoDTO> listarConsumosEspecificos(
        @NotNull Integer page,
        @NotNull Integer size
    ) {
        return queryConsumoUC.list(page, size);
    }

    @Query(value = "consumoEspecificoPorId")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {GET_SPECIFIC_CONSUMPTION_BY_ID})
    public ConsumoEspecificoDTO buscarConsumoEspecificoPorId(Long id) {
        return queryConsumoUC.findById(id);
    }

    @Query(value = "consumosEspecificosPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {LIST_SPECIFIC_CONSUMPTIONS})
    public List<ConsumoEspecificoDTO> listarConsumosEspecificosPorRevisao(Long revisaoId) {
        return queryConsumoUC.findByRevisao(revisaoId);
    }

    // ==================== CÁLCULO DE CONSUMO DE ÁREA ====================

    /**
     * Calcula o consumo de área para uma área específica.
     * Fórmula: ConsumoArea = Producao × ConsumoEspecifico
     * Exceção: Mineração usa soma das produções de Beneficiamento.
     */
    @Query(value = "calcConsumoArea")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_AREA_CONSUMPTION})
    public ConsumoAreaResultDTO calcularConsumoArea(
            @NotNull Long areaId,
            @NotNull Long revisaoId
    ) {
        return calcConsumoAreaUC.calcular(areaId, revisaoId);
    }

    /**
     * Calcula o consumo de todas as áreas para uma revisão.
     * Retorna lista com consumo mensal de cada área em kWh.
     */
    @Query(value = "calcConsumoAreasPorRevisao")
    @SecuredAccess(roles = {ADMIN}, permissionsRequired = {CALC_AREA_CONSUMPTION_BY_REVISION})
    public List<ConsumoAreaResultDTO> calcularConsumoAreasPorRevisao(
            @NotNull Long revisaoId
    ) {
        return calcConsumoAreaUC.calcularPorRevisao(revisaoId);
    }
}
