package br.com.wisebyte.samarco.graphql.query.producao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.producao.ListPlanejamentoProducaoUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoProducaoMapper;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.*;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class PlanejamentoProducaoQuery {

    @Inject
    PlanejamentoProducaoRepository repository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Inject
    PlanejamentoProducaoMapper mapper;

    @Inject
    ListPlanejamentoProducaoUC listPlanejamentoProducaoUC;

    @Query("listarPlanejamentosProducaoPaginado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PRODUCTION_PLANNINGS}
    )
    public ListResults<PlanejamentoProducaoDTO> listarPlanejamentosProducaoPaginado(
            List<FilterInput> filters,
            String sortBy,
            SortDirectionDTO sortDirection,
            @NotNull Integer page,
            @NotNull Integer size) {
        Map<String, Object> finalFilters = Optional.ofNullable(filters)
                .orElse(Collections.emptyList())
                .stream()
                .filter(f -> f.key() != null && f.value() != null)
                .collect(Collectors.toMap(FilterInput::key, FilterInput::value, (a, b) -> b));

        String column = Optional.ofNullable(sortBy).orElse("id");
        String sortDir = Optional.ofNullable(sortDirection)
                .map(SortDirectionDTO::name)
                .orElse("ASC");

        return mapper.toDTO(listPlanejamentoProducaoUC.list(finalFilters, column, sortDir, page, size));
    }

    @Query("listarPlanejamentosProducao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PRODUCTION_PLANNINGS}
    )
    public List<PlanejamentoProducaoDTO> listarPlanejamentosProducao() {
        return repository.findAll()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("buscarPlanejamentoProducaoPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PRODUCTION_PLANNING_BY_ID}
    )
    public PlanejamentoProducaoDTO buscarPlanejamentoProducaoPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Query("listarPlanejamentosProducaoPorRevisao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PRODUCTION_PLANNINGS_BY_REVISION}
    )
    public List<PlanejamentoProducaoDTO> listarPlanejamentosProducaoPorRevisao(
            Long revisaoId
    ) {
        return revisaoRepository.findById(revisaoId)
                .map(revisao -> repository.findByRevisao(revisao).stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList())
                )
                .orElse(List.of());
    }

    @Query("buscarPlanejamentoProducaoPorRevisaoEArea")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PRODUCTION_PLANNING_BY_REVISION_AND_AREA}
    )
    public PlanejamentoProducaoDTO buscarPlanejamentoProducaoPorRevisaoEArea(
            Long revisaoId,
            Long areaId
    ) {
        Revisao revisao = revisaoRepository.findById(revisaoId).orElse(null);
        Area area = areaRepository.findById(areaId).orElse(null);

        if (revisao == null || area == null) {
            return null;
        }

        return repository.findByRevisaoAndArea(revisao, area)
                .map(mapper::toDTO)
                .orElse(null);
    }
}