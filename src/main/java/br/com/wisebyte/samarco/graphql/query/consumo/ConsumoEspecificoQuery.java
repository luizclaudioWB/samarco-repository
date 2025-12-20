package br.com.wisebyte.samarco.graphql.query.consumo;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.consumo.QueryConsumoEspecificoUC;
import br.com.wisebyte.samarco.dto.QueryList;
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
}
