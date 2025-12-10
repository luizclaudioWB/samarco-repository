package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaDistribuidoraMapper;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.model.planejamento.tarifa._TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryTarifaDistribuidoraUC {

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaDistribuidoraMapper mapper;

    public QueryList<TarifaDistribuidoraDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<TarifaDistribuidora> all = repository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _TarifaDistribuidora.periodoInicial.desc( ) ) );
        return QueryList.<TarifaDistribuidoraDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( mapper::toDTO ).toList( ) )
                .build( );
    }

    public TarifaDistribuidoraDTO findById( Long id ) {
        return repository.findById( id ).map( mapper::toDTO ).orElse( null );
    }

    public QueryList<TarifaDistribuidoraDTO> findByTarifaPlanejamento( @NotNull Long tarifaPlanejamentoId ) {
        return mapper.toQueryDTO( repository.findByTarifaPlanejamento( TarifaPlanejamento.builder( ).id( tarifaPlanejamentoId ).build( ) ) );
    }

    public QueryList<TarifaDistribuidoraDTO> findByDistribuidora( @NotNull Long distribuidoraId ) {
        return mapper.toQueryDTO( repository.findByDistribuidora( Distribuidora.builder( ).id( distribuidoraId ).build( ) ) );
    }
}
