package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.ListTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaDistribuidoraMapper;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
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
public class TarifaDistribuidoraQuery {

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    TarifaDistribuidoraMapper mapper;

    @Inject
    ListTarifaDistribuidoraUC listTarifaDistribuidoraUC;

    @Query("listarTarifasDistribuidoraPaginado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES}
    )
    public ListResults<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPaginado(
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

        return mapper.toDTO(listTarifaDistribuidoraUC.list(finalFilters, column, sortDir, page, size));
    }

    @Query("listarTarifasDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES}
    )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidora() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("buscarTarifaDistribuidoraPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_DISTRIBUTOR_RATE_BY_ID}
    )
    public TarifaDistribuidoraDTO buscarTarifaDistribuidoraPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Query("listarTarifasDistribuidoraPorTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES}
    )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorTarifaPlanejamento(
            Long tarifaPlanejamentoId
    ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById(tarifaPlanejamentoId)
                .orElse(null);

        if (tp == null) {
            return List.of();
        }

        return repository.findByPlanejamento(tp).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("listarTarifasDistribuidoraPorDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES}
    )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorDistribuidora(
            Long distribuidoraId
    ) {
        Distribuidora dist = distribuidoraRepository
                .findById(distribuidoraId)
                .orElse(null);

        if (dist == null) {
            return List.of();
        }

        return repository.findByDistribuidora(dist).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca tarifas de uma distribuidora em um planejamento especifico
     *
     * CHAIN OF THOUGHTS:
     * - A query mais especifica
     * - "Qual a tarifa da CEMIG na Revisao 1 de 2026?"
     * - Pode retornar varias (periodos diferentes)
     */
    @Query("listarTarifasDistribuidoraPorPlanejamentoEDistribuidora")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES}
    )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorPlanejamentoEDistribuidora(
            Long tarifaPlanejamentoId,
            Long distribuidoraId
    ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById(tarifaPlanejamentoId)
                .orElse(null);

        Distribuidora dist = distribuidoraRepository
                .findById(distribuidoraId)
                .orElse(null);

        if (tp == null || dist == null) {
            return List.of();
        }

        return repository.findByPlanejamentoAndDistribuidora(tp, dist).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
