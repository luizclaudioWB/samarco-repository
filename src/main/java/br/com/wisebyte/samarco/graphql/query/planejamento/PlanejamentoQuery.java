package br.com.wisebyte.samarco.graphql.query.planejamento;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.planejamento.ListPlanejamentoUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoMapper;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
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
public class PlanejamentoQuery {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoMapper planejamentoMapper;

    @Inject
    ListPlanejamentoUC listPlanejamentoUC;


    @Query( value = "listarPlanejamentosPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PLANNINGS} )
    public ListResults<PlanejamentoDTO> listarPlanejamentosPaginado(
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

        return planejamentoMapper.toDTO( listPlanejamentoUC.list( finalFilters, column, sortDir, page, size ) );
    }


    @Query( value = "listarPlanejamentos" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_PLANNINGS} )
    public List<PlanejamentoDTO> listarPlanejamentos( ) {
        return planejamentoRepository.findAll( )
                .map( planejamentoMapper::toDTO )
                .collect( Collectors.toList( ) );
    }


    @Query( value = "buscarPlanejamentoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PLANNING_BY_ID} )
    public PlanejamentoDTO buscarPlanejamentoPorId( Long id ) {
        return planejamentoRepository.findById( id )
                .map( planejamentoMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "buscarPlanejamentoPorAno" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_PLANNING_BY_YEAR} )
    public PlanejamentoDTO buscarPlanejamentoPorAno( Integer ano ) {
        return planejamentoRepository.findByAno( ano )
                .map( planejamentoMapper::toDTO )
                .orElse( null );
    }


    @Query( value = "buscarPlanejamentoCorrente" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_CURRENT_PLANNING} )
    public PlanejamentoDTO buscarPlanejamentoCorrente( ) {
        return planejamentoRepository.findByCorrente( true )
                .map( planejamentoMapper::toDTO )
                .orElse( null );
    }
}
