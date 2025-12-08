package br.com.wisebyte.samarco.graphql.query.tarifa;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.tarifa.QueryTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.dto.QueryList;
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

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class TarifaDistribuidoraQuery {

    @Inject
    QueryTarifaDistribuidoraUC queryTarifaDistribuidoraUC;

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    TarifaDistribuidoraMapper mapper;

    @Query( value = "tarifasDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public QueryList<TarifaDistribuidoraDTO> listarTarifasDistribuidora( @NotNull Integer page, @NotNull Integer size ) {
        return queryTarifaDistribuidoraUC.list( page, size );
    }

    @Query( value = "tarifaDistribuidoraPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_DISTRIBUTOR_RATE_BY_ID} )
    public TarifaDistribuidoraDTO buscarTarifaDistribuidoraPorId( Long id ) {
        return queryTarifaDistribuidoraUC.findById( id );
    }

    @Query( value = "tarifasDistribuidoraPorTarifaPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorTarifaPlanejamento( Long tarifaPlanejamentoId ) {
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

    @Query( value = "tarifasDistribuidoraPorDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorDistribuidora( Long distribuidoraId ) {
        Distribuidora dist = distribuidoraRepository
                .findById( distribuidoraId )
                .orElse( null );

        if ( dist == null ) {
            return List.of( );
        }

        return repository.findByDistribuidora( dist ).stream( )
                .map( mapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "tarifasDistribuidoraPorPlanejamentoEDistribuidora" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_DISTRIBUTOR_RATES} )
    public List<TarifaDistribuidoraDTO> listarTarifasDistribuidoraPorPlanejamentoEDistribuidora(
            Long tarifaPlanejamentoId,
            Long distribuidoraId ) {
        TarifaPlanejamento tp = tarifaPlanejamentoRepository
                .findById( tarifaPlanejamentoId )
                .orElse( null );

        Distribuidora dist = distribuidoraRepository
                .findById( distribuidoraId )
                .orElse( null );

        if ( tp == null || dist == null ) {
            return List.of( );
        }

        return repository.findByPlanejamentoAndDistribuidora( tp, dist ).stream( )
                .map( mapper::toDTO )
                .collect( Collectors.toList( ) );
    }
}
