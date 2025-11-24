package br.com.wisebyte.samarco.rest.client.bc;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient( configKey = "aneel-api" )
public interface ANEELAPIRestClient {

    @GET
    @Path( "/dataset/5a583f3e-1646-4f67-bf0f-69db4203e89e/resource/fcf2906c-7c32-4b9b-a637-054e7a5234f4/download/tarifas-homologadas-distribuidoras-energia-eletrica.csv" )
    @Produces( MediaType.APPLICATION_OCTET_STREAM )
        // Indicamos que queremos os dados "crus" (bytes)
    Response baixarArquivoTarifas( );


}
