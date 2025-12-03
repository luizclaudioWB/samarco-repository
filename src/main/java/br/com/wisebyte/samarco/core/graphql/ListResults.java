package br.com.wisebyte.samarco.core.graphql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class ListResults<DTO> {

    Long count;

    Integer currentPage;

    Integer pageSize;

    Integer totalPages;

    List<DTO> results;
}
