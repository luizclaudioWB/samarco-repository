package br.com.wisebyte.samarco.graphql.query.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.distribuidora.ListDistribuidoraUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
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
public class DistribuidoraQuery {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    @Inject
    ListDistribuidoraUC listDistribuidoraUC;

    @Query( value = "listarDistribuidorasPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTORS} )
    public ListResults<DistribuidoraDTO> listarDistribuidorasPaginado(
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

        return distribuidoraMapper.toDTO( listDistribuidoraUC.list( finalFilters, column, sortDir, page, size ) );
    }

    @Query( value = "listarDistribuidoras" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTORS} )
    public List<DistribuidoraDTO> listarDistribuidoras( ) {
        return distribuidoraRepository.findAll( )
                .map( distribuidoraMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarDistribuidoraPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_DISTRIBUTOR_BY_ID} )
    public DistribuidoraDTO buscarDistribuidoraPorId( Long id ) {
        return distribuidoraRepository.findById( id )
                .map( distribuidoraMapper::toDTO )
                .orElse( null );
    }
}
