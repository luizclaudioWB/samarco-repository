package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.mapper.revisao.RevisaoMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento._Revisao;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QueryRevisaoUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    public QueryList<RevisaoDTO> list( Integer page, Integer size ) {
        Page<Revisao> all = revisaoRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Revisao.id.asc( ) ) );
        return QueryList.<RevisaoDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( revisaoMapper::toDTO ).toList( ) )
                .build( );
    }

    public RevisaoDTO findById( Long id ) {
        return revisaoRepository.findById( id ).map( revisaoMapper::toDTO ).orElse( null );
    }

    public QueryList<RevisaoDTO> listRevisoesOficiais( ) {
        List<Revisao> byOficial = revisaoRepository.findByOficial( true );
        return QueryList.<RevisaoDTO>builder( )
                .totalElements( ( long ) byOficial.size( ) )
                .totalPages( 1L )
                .results( byOficial.stream( ).map( revisaoMapper::toDTO ).toList( ) )
                .build( );
    }

    public QueryList<RevisaoDTO> findByPlanejamento( @NotNull Long idPlanejamento ) {
        List<Revisao> reviews = revisaoRepository.findByPlanejamentoId( idPlanejamento );
        return QueryList.<RevisaoDTO>builder( )
                .totalElements( ( long ) reviews.size( ) )
                .totalPages( 1L )
                .results( reviews.stream( ).map( revisaoMapper::toDTO ).toList( ) )
                .build( );
    }
}
