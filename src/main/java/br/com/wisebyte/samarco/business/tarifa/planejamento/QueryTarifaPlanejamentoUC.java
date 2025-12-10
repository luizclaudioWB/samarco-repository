package br.com.wisebyte.samarco.business.tarifa.planejamento;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaPlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.model.planejamento.tarifa._TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryTarifaPlanejamentoUC {

    @Inject
    TarifaPlanejamentoRepository repository;

    @Inject
    TarifaPlanejamentoMapper mapper;

    public QueryList<TarifaPlanejamentoDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<TarifaPlanejamento> all = repository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _TarifaPlanejamento.id.desc( ) ) );
        return QueryList.<TarifaPlanejamentoDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( mapper::toDTO ).toList( ) )
                .build( );
    }

    public TarifaPlanejamentoDTO findById( Long id ) {
        return repository.findById( id ).map( mapper::toDTO ).orElse( null );
    }

    public TarifaPlanejamentoDTO findByRevisao( @NotNull Long revisaoId ) {
        return repository.findByRevisao( Revisao.builder( ).id( revisaoId ).build( ) ).stream( ).map( mapper::toDTO ).findFirst( ).orElse( null );
    }
}
