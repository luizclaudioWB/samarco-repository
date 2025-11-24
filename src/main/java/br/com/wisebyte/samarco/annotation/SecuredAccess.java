package br.com.wisebyte.samarco.annotation;


import br.com.wisebyte.samarco.auth.Permissao;
import br.com.wisebyte.samarco.auth.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.TYPE} )
@Retention( RetentionPolicy.RUNTIME )
public @interface SecuredAccess {
    /**
     * É preciso ter uma das roles para acessar o endpoint
     */
    Role[] roles( ) default {};

    /**
     * É preciso ter todas as permissões para acessar o endpoint
     */
    Permissao[] permissionsRequired( ) default {};
}
