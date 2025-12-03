package br.com.wisebyte.samarco.dto.graphql;

import org.eclipse.microprofile.graphql.Input;

@Input("FilterInput")
public record FilterInput(
        String key,
        String value
) {
}
