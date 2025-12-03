package br.com.wisebyte.samarco.graphql.query.fornecedor;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.fornecedor.ListFornecedorUC;
import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.dto.graphql.FilterInput;
import br.com.wisebyte.samarco.dto.graphql.SortDirectionDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
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
public class FornecedorQuery {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    @Inject
    ListFornecedorUC listFornecedorUC;

    @Query( value = "listarFornecedoresPaginado" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIERS} )
    public ListResults<FornecedorDTO> listarFornecedoresPaginado(
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

        return fornecedorMapper.toDTO( listFornecedorUC.list( finalFilters, column, sortDir, page, size ) );
    }

    @Query( value = "listarFornecedores" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIERS} )
    public List<FornecedorDTO> listarFornecedores( ) {
        return fornecedorRepository.findAll( )
                .map( fornecedorMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarFornecedorPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SUPPLIER_BY_ID} )
    public FornecedorDTO buscarFornecedorPorId( Long id ) {
        return fornecedorRepository.findById( id )
                .map( fornecedorMapper::toDTO )
                .orElse( null );
    }
}
