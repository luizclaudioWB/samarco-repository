package br.com.wisebyte.samarco.graphql.query.producaoconfig;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producaoconfig.QueryProducaoConfigUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.GET_PRODUCTION_CONFIG_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_PRODUCTION_CONFIGS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class ProducaoConfigQuery {

    @Inject
    QueryProducaoConfigUC queryProducaoConfigUC;

    @Query("producoesConfig")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {LIST_PRODUCTION_CONFIGS}
    )
    public QueryList<ProducaoConfigDTO> listarProducoesConfig(
        @NotNull Integer page,
        @NotNull Integer size
    ) {
        return queryProducaoConfigUC.list(page, size);
    }

    @Query("producaoConfigPorId")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {GET_PRODUCTION_CONFIG_BY_ID}
    )
    public ProducaoConfigDTO buscarProducaoConfigPorId(Long id) {
        return queryProducaoConfigUC.findById(id);
    }

    @Query("producaoConfigPorRevisao")
    @SecuredAccess(
        roles = {ADMIN},
        permissionsRequired = {GET_PRODUCTION_CONFIG_BY_ID}
    )
    public ProducaoConfigDTO buscarProducaoConfigPorRevisao(Long revisaoId) {
        return queryProducaoConfigUC.findByRevisaoId(revisaoId);
    }
}
