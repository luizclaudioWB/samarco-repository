package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.model.unidade._Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    public QueryList<UnidadeDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<Unidade> all = unidadeRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Unidade.nomeUnidade.asc( ) ) );
        return QueryList.<UnidadeDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( unidadeMapper::toDTO ).toList( ) )
                .build( );
    }

    public UnidadeDTO findById( Long id ) {
        return unidadeRepository.findById( id ).map( unidadeMapper::toDTO ).orElse( null );
    }

}
