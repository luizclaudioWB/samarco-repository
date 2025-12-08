package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.QueryTarifaFornecedorUC;
import br.com.wisebyte.samarco.dto.QueryList;
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

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaFornecedorQuery {

    @Inject
    QueryTarifaFornecedorUC queryTarifaFornecedorUC;

    @Inject
    TarifaFornecedorRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TarifaFornecedorMapper mapper;

    @Query( value = "tarifasFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public QueryList<TarifaFornecedorDTO> listarTarifasFornecedor( @NotNull Integer page, @NotNull Integer size ) {
        return queryTarifaFornecedorUC.list( page, size );
    }

    @Query( value = "tarifaFornecedorPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_SUPPLIER_RATE_BY_ID} )
    public TarifaFornecedorDTO buscarTarifaFornecedorPorId( Long id ) {
        return queryTarifaFornecedorUC.findById( id );
    }

    @Query( value = "tarifasFornecedorPorTarifaPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorTarifaPlanejamento( Long tarifaPlanejamentoId ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById( tarifaPlanejamentoId )
                .orElse( null );

        if ( tp == null ) {
            return List.of( );
        }

        return repository.findByPlanejamento( tp ).stream( )
                .map( mapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "tarifasFornecedorPorFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorFornecedor( Long fornecedorId ) {
        Fornecedor fornecedor = fornecedorRepository
                .findById( fornecedorId )
                .orElse( null );

        if ( fornecedor == null ) {
            return List.of( );
        }

        return repository.findByFornecedor( fornecedor ).stream( )
                .map( mapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "tarifasFornecedorPorPlanejamentoEFornecedor" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_SUPPLIER_RATES} )
    public List<TarifaFornecedorDTO> listarTarifasFornecedorPorPlanejamentoEFornecedor(
            Long tarifaPlanejamentoId,
            Long fornecedorId ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById( tarifaPlanejamentoId )
                .orElse( null );

        Fornecedor fornecedor = fornecedorRepository
                .findById( fornecedorId )
                .orElse( null );

        if ( tp == null || fornecedor == null ) {
            return List.of( );
        }

        return repository.findByPlanejamentoAndFornecedor( tp, fornecedor ).stream( )
                .map( mapper::toDTO )
                .collect( Collectors.toList( ) );
    }
}
