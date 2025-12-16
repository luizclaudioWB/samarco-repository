package br.com.wisebyte.samarco.business.ipca;

import br.com.wisebyte.samarco.rest.client.bc.BancoCentralRestClient;
import io.quarkus.logging.Log;
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
        var dados = restClient.consultarSeriePorPeriodo( 16122, DTF.format( inicio ), DTF.format( fim ), "json" );

        if ( dados == null || dados.isEmpty( ) ) {
            Log.warnf( "[IPCA] BC retornou vazio: %s a %s", DTF.format( inicio ), DTF.format( fim ) );
            return 0d;
        }

        return dados.stream( )
                .map( it -> parseDouble( it.percentual( ) ) )
                .reduce( Double::sum )
                .orElse( 0d );
    }
}
