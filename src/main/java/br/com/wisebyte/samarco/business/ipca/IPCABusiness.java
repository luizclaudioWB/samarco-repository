package br.com.wisebyte.samarco.business.ipca;

import br.com.wisebyte.samarco.rest.client.bc.BancoCentralRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Double.parseDouble;
import static java.time.format.DateTimeFormatter.ofPattern;

@ApplicationScoped
public class IPCABusiness {

    final DateTimeFormatter DTF = ofPattern( "dd/MM/yyyy" );

    @Inject
    @RestClient
    BancoCentralRestClient restClient;

    public Double calculaIPCAPeriodo( @NotNull LocalDate inicio, @NotNull LocalDate fim ) {
        return restClient.consultarSeriePorPeriodo( 16122, DTF.format( inicio ), DTF.format( fim ), "json" )
                .stream( ).map( it -> parseDouble( it.percentual( ) ) )
                .reduce( Double::sum ).orElse( 0d );
    }
}
