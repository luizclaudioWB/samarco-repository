package br.com.wisebyte.samarco;

import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TarifasTest {

    @Inject
    EntityManager entityManager;

    @Test
    @Transactional
    public void testeTarifas( ) {
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "ONS" )
                        .cnpj( "02.831.210/0001-57" )
                        .siglaAgente( "ONS" )
                        .qtdeDeHorasPonta( 3 )
                        .build( )
        );
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "EDP" )
                        .cnpj( "28.152.650/0001-71" )
                        .siglaAgente( "EDP" )
                        .qtdeDeHorasPonta( 3 )
                        .build( )
        );
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "ENERGISA" )
                        .cnpj( "19.527.639/0001-58" )
                        .siglaAgente( "ENERGISA" )
                        .qtdeDeHorasPonta( 3 )
                        .build( )
        );
    }
}
