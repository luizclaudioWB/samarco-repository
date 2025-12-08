package br.com.wisebyte.samarco.core.mapper;

import br.com.wisebyte.samarco.dto.QueryList;

import java.util.List;


public interface EntityMapper<ENTITY, DTO> {

    DTO toDTO( ENTITY entity );

    ENTITY toEntity( DTO dto );

    default List<DTO> toDTO( List<ENTITY> result ) {
        return result.stream( ).map( this::toDTO ).toList( );
    }

    default QueryList<DTO> toQueryDTO( List<ENTITY> result ) {
        List<DTO> list = result.stream( ).map( this::toDTO ).toList( );
        return QueryList.<DTO>builder( )
                .results( list )
                .totalElements( ( long ) list.size( ) )
                .totalPages( 1L )
                .build( );
    }
}
