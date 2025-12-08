package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaDistribuidoraMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
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
    TarifaDistribuidoraRepository tarifaDistribuidoraRepository;

    @Inject
    TarifaDistribuidoraMapper tarifaDistribuidoraMapper;

    public QueryList<TarifaDistribuidoraDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<TarifaDistribuidora> all = tarifaDistribuidoraRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _TarifaDistribuidora.periodoInicial.desc( ) ) );
        return QueryList.<TarifaDistribuidoraDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( tarifaDistribuidoraMapper::toDTO ).toList( ) )
                .build( );
    }

    public TarifaDistribuidoraDTO findById( Long id ) {
        return tarifaDistribuidoraRepository.findById( id ).map( tarifaDistribuidoraMapper::toDTO ).orElse( null );
    }

}
