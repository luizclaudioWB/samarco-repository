package br.com.wisebyte.samarco.dto;

import lombok.*;
import org.eclipse.microprofile.graphql.Description;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Description( "Query List Result" )
public class QueryList<T> {

    @Description( "Total of elements" )
    private Long totalElements;

    @Description( "Total of pages" )
    private Long totalPages;

    @Description( "List of results" )
    private List<T> results;

}
