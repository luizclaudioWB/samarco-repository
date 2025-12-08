package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.mapper.area.AreaMapper;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.area._Area;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryAreaUC {

    @Inject
    AreaRepository areaRepository;

    @Inject
    AreaMapper areaMapper;

    public QueryList<AreaDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<Area> all = areaRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Area.nomeArea.asc( ) ) );
        return QueryList.<AreaDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( areaMapper::toDTO ).toList( ) )
                .build( );
    }

    public AreaDTO findById( Long id ) {
        return areaRepository.findById( id ).map( areaMapper::toDTO ).orElse( null );
    }

}
