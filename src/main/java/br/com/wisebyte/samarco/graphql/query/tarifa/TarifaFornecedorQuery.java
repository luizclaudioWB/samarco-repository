package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.ListTarifaFornecedorUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaFornecedorMapper;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
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
public class TarifaFornecedorQuery {

    @Inject
    TarifaFornecedorRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TarifaFornecedorMapper mapper;

    @Inject
    ListTarifaFornecedorUC listTarifaFornecedorUC;

    @Query("listarTarifasFornecedorPaginado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES}
    )
    public ListResults<TarifaFornecedorDTO> listarTarifasFornecedorPaginado(
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

        return mapper.toDTO(listTarifaFornecedorUC.list(finalFilters, column, sortDir, page, size));
    }

    @Query("listarTarifasFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES}
    )
    public List<TarifaFornecedorDTO> listarTarifasFornecedor() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("buscarTarifaFornecedorPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SUPPLIER_RATE_BY_ID}
    )
    public TarifaFornecedorDTO buscarTarifaFornecedorPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Query("listarTarifasFornecedorPorTarifaPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES}
    )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorTarifaPlanejamento(
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

    @Query("listarTarifasFornecedorPorFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES}
    )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorFornecedor(
            Long fornecedorId
    ) {
        Fornecedor fornecedor = fornecedorRepository
                .findById(fornecedorId)
                .orElse(null);

        if (fornecedor == null) {
            return List.of();
        }

        return repository.findByFornecedor(fornecedor).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("listarTarifasFornecedorPorPlanejamentoEFornecedor")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES}
    )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorPlanejamentoEFornecedor(
            Long tarifaPlanejamentoId,
            Long fornecedorId
    ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById(tarifaPlanejamentoId)
                .orElse(null);

        Fornecedor fornecedor = fornecedorRepository
                .findById(fornecedorId)
                .orElse(null);

        if (tp == null || fornecedor == null) {
            return List.of();
        }

        return repository.findByPlanejamentoAndFornecedor(tp, fornecedor).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
