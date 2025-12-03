package br.com.wisebyte.samarco.core.graphql;

import lombok.Builder;

import java.util.List;

@Builder
public record GraphQLQueryList<ENTITY>(
        List<ENTITY> entities,
        Long count,
        Integer totalOfPages,
        Integer page,
        Integer size
) {
}
