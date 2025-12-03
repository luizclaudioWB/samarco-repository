package br.com.wisebyte.samarco.graphql.query.revisao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.revisao.ListRevisaoUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.mapper.revisao.RevisaoMapper;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
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
public class RevisaoQuery {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    @Inject
    ListRevisaoUC listRevisaoUC;

    @Query( value = "listarRevisoesPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_REVISIONS} )
    public ListResults<RevisaoDTO> listarRevisoesPaginado(
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

        return revisaoMapper.toDTO( listRevisaoUC.list( finalFilters, column, sortDir, page, size ) );
    }

    @Query( value = "listarRevisoes" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_REVISIONS} )
    public List<RevisaoDTO> listarRevisoes( ) {
        return revisaoRepository.findAll( )
                .map( revisaoMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarRevisaoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_REVISION_BY_ID} )
    public RevisaoDTO buscarRevisaoPorId( Long id ) {
        return revisaoRepository.findById( id )
                .map( revisaoMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "listarRevisoesOficiais" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_OFFICIAL_REVISIONS} )
    public List<RevisaoDTO> listarRevisoesOficiais( ) {
        return revisaoRepository.findByOficial( true ).stream( )
                .map( revisaoMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "listarRevisoesPorPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_REVISIONS_BY_PLANNING} )
    public List<RevisaoDTO> listarRevisoesPorPlanejamento( Long planejamentoId ) {
        return planejamentoRepository.findById( planejamentoId )
                .map( planejamento -> revisaoRepository.findByPlanejamento( planejamento ).stream( )
                        .map( revisaoMapper::toDTO )
                        .collect( Collectors.toList( ) )
                )
                .orElse( List.of( ) );
    }
}
