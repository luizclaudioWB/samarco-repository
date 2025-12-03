package br.com.wisebyte.samarco.graphql.query.area;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.area.ListAreaUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.mapper.area.AreaMapper;
import br.com.wisebyte.samarco.model.area.TipoArea;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
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
public class AreaQuery {

    @Inject
    AreaRepository areaRepository;

    @Inject
    AreaMapper areaMapper;

    @Inject
    ListAreaUC listAreaUC;

    @Query( value = "listarAreasPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_AREAS} )
    public ListResults<AreaDTO> listarAreasPaginado(
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

        return areaMapper.toDTO( listAreaUC.list( finalFilters, column, sortDir, page, size ) );
    }

    @Query( value = "listarAreas" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_AREAS} )
    public List<AreaDTO> listarAreas( ) {
        return areaRepository.findAll( )
                .map( areaMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarAreaPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_AREA_BY_ID} )
    public AreaDTO buscarAreaPorId( Long id ) {
        return areaRepository.findById( id )
                .map( areaMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "listarAreasPorTipo" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_AREAS_BY_TYPE} )
    public List<AreaDTO> listarAreasPorTipo( TipoArea tipoArea ) {
        return areaRepository.findByTipoArea( tipoArea ).stream( )
                .map( areaMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "listarAreasAtivas" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_ACTIVE_AREAS} )
    public List<AreaDTO> listarAreasAtivas( ) {
        return areaRepository.findByAtivo( true ).stream( )
                .map( areaMapper::toDTO )
                .collect( Collectors.toList( ) );
    }
}
