package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.ListTarifaPlanejamentoUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaPlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
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
public class TarifaPlanejamentoQuery {

    @Inject
    TarifaPlanejamentoRepository repository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    TarifaPlanejamentoMapper mapper;

    @Inject
    ListTarifaPlanejamentoUC listTarifaPlanejamentoUC;


    @Query("listarTarifasPlanejamentoPaginado")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_RATE_PLANNINGS}
    )
    public ListResults<TarifaPlanejamentoDTO> listarTarifasPlanejamentoPaginado(
            List<FilterInput> filters,
            String sortBy,
            SortDirectionDTO sortDirection,
            @NotNull Integer page,
            @NotNull Integer size ) {
        Map<String, Object> finalFilters = Optional.ofNullable( filters )
                .orElse( Collections.emptyList() )
                .stream()
                .filter( f -> f.key() != null && f.value() != null )
                .collect( Collectors.toMap( FilterInput::key, FilterInput::value, ( a, b ) -> b ) );

        String column = Optional.ofNullable( sortBy ).orElse( "id" );
        String sortDir = Optional.ofNullable( sortDirection )
                .map( SortDirectionDTO::name )
                .orElse( "ASC" );

        return mapper.toDTO( listTarifaPlanejamentoUC.list( finalFilters, column, sortDir, page, size ) );
    }

    @Query("listarTarifasPlanejamento")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_RATE_PLANNINGS}
    )
    public List<TarifaPlanejamentoDTO> listarTarifasPlanejamento() {
        return repository.findAll()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Query("buscarTarifaPlanejamentoPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_RATE_PLANNING_BY_ID}
    )
    public TarifaPlanejamentoDTO buscarTarifaPlanejamentoPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }


    @Query("buscarTarifaPlanejamentoPorRevisao")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_RATE_PLANNING_BY_REVISION}
    )
    public TarifaPlanejamentoDTO buscarTarifaPlanejamentoPorRevisao(Long revisaoId) {
        Revisao revisao = revisaoRepository.findById(revisaoId).orElse(null);

        if (revisao == null) {
            return null;
        }

        return repository.findByRevisao(revisao)
                .map(mapper::toDTO)
                .orElse(null);
    }
}
