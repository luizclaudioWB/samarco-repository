package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoProducaoMapper;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.model.producao._PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;


@ApplicationScoped
public class QueryPlanejamentoProducaoUC {

    @Inject
    PlanejamentoProducaoRepository repository;

    @Inject
    PlanejamentoProducaoMapper mapper;

    public QueryList<PlanejamentoProducaoDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<PlanejamentoProducao> all = repository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _PlanejamentoProducao.id.asc( ) ) );
        return QueryList.<PlanejamentoProducaoDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( mapper::toDTO ).toList( ) )
                .build( );
    }

    public PlanejamentoProducaoDTO findById( Long id ) {
        return repository.findById( id ).map( mapper::toDTO ).orElse( null );
    }
}
