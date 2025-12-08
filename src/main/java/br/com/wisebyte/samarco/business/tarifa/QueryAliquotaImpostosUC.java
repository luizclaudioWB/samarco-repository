package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.mapper.tarifa.AliquotaImpostosMapper;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.model.planejamento.tarifa._AliquotaImpostos;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QueryAliquotaImpostosUC {

    @Inject
    AliquotaImpostosRepository repository;

    @Inject
    AliquotaImpostosMapper mapper;

    public QueryList<AliquotaImpostosDTO> list( Integer page, Integer size ) {
        Page<AliquotaImpostos> all = repository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _AliquotaImpostos.id.asc( ) ) );
        return QueryList.<AliquotaImpostosDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( mapper::toDTO ).toList( ) )
                .build( );
    }

    public AliquotaImpostosDTO findById( Long id ) {
        return repository.findById( id ).map( mapper::toDTO ).orElse( null );
    }

    public QueryList<AliquotaImpostosDTO> findByEstado( Estado estado ) {
        return mapper.toQueryDTO( repository.findByEstado( estado ) );
    }

    public QueryList<AliquotaImpostosDTO> findByTarifaPlanejamento( Long tarifaPlanejamentoId ) {
        return mapper.toQueryDTO( repository.findByTarifaPlanejamento( TarifaPlanejamento.builder( ).id( tarifaPlanejamentoId ).build( ) ) );
    }

}
