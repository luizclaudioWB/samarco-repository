package br.com.wisebyte.samarco.core.mapper;

import br.com.wisebyte.samarco.core.graphql.ListResults;
import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;


public interface EntityMapper<ENTITY, DTO> {


    DTO toDTO( ENTITY entity );

    ENTITY toEntity( DTO dto );

    default ListResults<DTO> toDTO(GraphQLQueryList<ENTITY> result ) {
        return ListResults.<DTO>builder()
                .count( result.count() )
                .currentPage( result.page() )
                .pageSize( result.size() )
                .totalPages( result.totalOfPages() )
                .results( result.entities().stream().map( this::toDTO ).toList() )
                .build();
    }
}
