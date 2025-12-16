package br.com.wisebyte.samarco.business.exception;

import io.quarkus.logging.Log;
import io.smallrye.graphql.api.ErrorExtensionProvider;
import jakarta.json.Json;
import jakarta.json.JsonValue;

public class ValidadeExceptionBusiness extends RuntimeException implements ErrorExtensionProvider {

    private String entity;

    private String title;

    public ValidadeExceptionBusiness( ) {
    }

    public ValidadeExceptionBusiness( String entity, String title, String message ) {
        super( message );
        this.entity = entity;
        this.title = title;
        Log.warnf( "[VALIDACAO] %s.%s: %s", entity, title, message );
    }

    @Override
    public String getKey( ) {
        return getTitle( );
    }

    public String getTitle( ) {
        return title;
    }

    @Override
    public JsonValue mapValueFrom( Throwable throwable ) {
        if ( throwable instanceof ValidadeExceptionBusiness e ) {
            return Json.createObjectBuilder( )
                    .add( "entity", e.getEntity( ) )
                    .add( "title", e.getTitle( ) )
                    .add( "message", e.getMessage( ) )
                    .build( );
        }
        return Json.createObjectBuilder( )
                .add( "message", throwable.getMessage( ) )
                .build( );
    }

    public String getEntity( ) {
        return entity;
    }
}
