package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.ListAliquotaImpostosUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.mapper.tarifa.AliquotaImpostosMapper;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class AliquotaImpostosQuery {

    @Inject
    AliquotaImpostosRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    AliquotaImpostosMapper mapper;

    @Inject
    ListAliquotaImpostosUC listAliquotaImpostosUC;

    @Query("listarAliquotasImpostosPaginado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public ListResults<AliquotaImpostosDTO> listarAliquotasImpostosPaginado(
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

        return mapper.toDTO(listAliquotaImpostosUC.list(finalFilters, column, sortDir, page, size));
    }

    @Query("listarAliquotasImpostos")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public List<AliquotaImpostosDTO> listarAliquotasImpostos() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Query("buscarAliquotaImpostosPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_TAX_RATE_BY_ID}
    )
    public AliquotaImpostosDTO buscarAliquotaImpostosPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }


    @Query("listarAliquotasPorEstado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES_BY_STATE}
    )
    public List<AliquotaImpostosDTO> listarAliquotasPorEstado(Estado estado) {
        return repository.findByEstado(estado).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("listarAliquotasPorAno")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public List<AliquotaImpostosDTO> listarAliquotasPorAno(Integer ano) {
        return repository.findByAno(ano).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Query("listarAliquotasPorTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_TAX_RATES}
    )
    public List<AliquotaImpostosDTO> listarAliquotasPorTarifaPlanejamento(Long tarifaPlanejamentoId) {
        TarifaPlanejamento tarifaPlanejamento = tarifaPlanejamentoRepository
                .findById(tarifaPlanejamentoId)
                .orElse(null);

        if (tarifaPlanejamento == null) {
            return List.of();
        }

        return repository.findByPlanejamento(tarifaPlanejamento).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
