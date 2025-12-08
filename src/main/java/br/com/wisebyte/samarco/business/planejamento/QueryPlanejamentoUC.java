package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.model.planejamento._Planejamento;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryPlanejamentoUC {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoMapper planejamentoMapper;

    public QueryList<PlanejamentoDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<Planejamento> all = planejamentoRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Planejamento.ano.asc( ) ) );
        return QueryList.<PlanejamentoDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( planejamentoMapper::toDTO ).toList( ) )
                .build( );
    }

    public PlanejamentoDTO findByAno( @NotNull Integer ano ) {
        return planejamentoRepository.findByAno( ano ).map( planejamentoMapper::toDTO ).orElse( null );
    }

}
