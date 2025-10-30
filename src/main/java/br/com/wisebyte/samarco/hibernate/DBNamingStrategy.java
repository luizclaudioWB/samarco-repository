package br.com.wisebyte.samarco.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static java.util.Objects.isNull;

public class DBNamingStrategy implements PhysicalNamingStrategy {

    public static final String A_Z_A_Z = "([a-z])([A-Z]+)";

    public static final String $_1_$_2 = "$1_$2";

    @Override
    public Identifier toPhysicalCatalogName( Identifier name, JdbcEnvironment jdbcEnvironment ) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName( Identifier name, JdbcEnvironment jdbcEnvironment ) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName( Identifier name, JdbcEnvironment jdbcEnvironment ) {
        return toLowerCaseIfNotNull( name );
    }

    private Identifier toLowerCaseIfNotNull( Identifier name ) {
        return !isNull( name ) ?
                new Identifier(
                        name
                                .getText( )
                                .replaceAll( A_Z_A_Z, $_1_$_2 )
                                .toLowerCase( ),
                        name.isQuoted( )
                ) :
                null;
    }

    @Override
    public Identifier toPhysicalSequenceName( Identifier name, JdbcEnvironment jdbcEnvironment ) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName( Identifier name, JdbcEnvironment jdbcEnvironment ) {
        return toLowerCaseIfNotNull( name );
    }
}
