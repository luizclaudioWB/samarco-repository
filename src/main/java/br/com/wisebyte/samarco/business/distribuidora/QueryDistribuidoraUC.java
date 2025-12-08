package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.distribuidora._Distribuidora;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class  QueryDistribuidoraUC {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;


    public QueryList<DistribuidoraDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<Distribuidora> all = distribuidoraRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Distribuidora.nome.asc( ) ) );
        return QueryList.<DistribuidoraDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( distribuidoraMapper::toDTO ).toList( ) )
                .build( );
    }

    public DistribuidoraDTO findById( Long id ) {
        return distribuidoraRepository.findById( id ).map( distribuidoraMapper::toDTO ).orElse( null );
    }
}
