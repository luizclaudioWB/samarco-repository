package br.com.wisebyte.samarco.rest.client.bc;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path( "/dados/serie" )
@RegisterRestClient( configKey = "banco-central-api" )
public interface BancoCentralRestClient {

    @GET
    @Path( "/bcdata.sgs.{codigoSerie}/dados" )
    @Produces( MediaType.APPLICATION_JSON )
    List<SerieDadoDTO> consultarSerie(
            @PathParam( "codigoSerie" ) Integer codigoSerie,
            @QueryParam( "formato" ) String formato
    );

    @GET
    @Path( "/bcdata.sgs.{codigoSerie}/dados" )
    @Produces( MediaType.APPLICATION_JSON )
    List<SerieDadoDTO> consultarSeriePorPeriodo(
            @PathParam( "codigoSerie" ) Integer codigoSerie,
            @QueryParam( "dataInicial" ) String dataInicial,
            @QueryParam( "dataFinal" ) String dataFinal,
            @QueryParam( "formato" ) String formato
    );

    record SerieDadoDTO(
            @JsonbProperty( "data" )
            String mes,

            @JsonbProperty( "valor" )
            String percentual
    ) { }
}
