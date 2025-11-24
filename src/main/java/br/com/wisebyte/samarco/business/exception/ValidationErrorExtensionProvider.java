package br.com.wisebyte.samarco.business.exception;

import io.smallrye.graphql.api.ErrorExtensionProvider;
import jakarta.json.Json;
import jakarta.json.JsonValue;

/**
 * Provider registrado via ServiceLoader para adicionar o campo "extensions"
 * nas respostas de erro do GraphQL. Ele trata especificamente a
 * ValidadeExceptionGraphQL, mas funciona de forma genérica para outras exceções.
 */
public class ValidationErrorExtensionProvider implements ErrorExtensionProvider {

    /**
     * Chave que aparecerá dentro de "extensions" no payload GraphQL.
     * Ex.: "extensions": { "validation": { ... } }
     */
    @Override
    public String getKey( ) {
        return "validation";
    }

    @Override
    public JsonValue mapValueFrom( Throwable throwable ) {
        if ( throwable instanceof ValidadeExceptionBusiness vex ) {
            return Json.createObjectBuilder( )
                    .add( "entity", vex.getEntity( ) )
                    .add( "title", vex.getTitle( ) )
                    .add( "message", vex.getMessage( ) )
                    .build( );
        }

        // Fallback genérico: expõe apenas a mensagem
        return Json.createObjectBuilder( )
                .add( "message", String.valueOf( throwable.getMessage( ) ) )
                .build( );
    }
}
