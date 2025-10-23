package br.com.wisebyte.samarco.model.estado;

public enum Estado {

    AC( "Acre" ),
    AL( "Alagoas" ),
    AP( "Amapa" ),
    AM( "Amazonas" ),
    BA( "Bahia" ),
    CE( "Ceará" ),
    DF( "Distrito Federal" ),
    ES( "Espirito Santo" ),
    GO( "Goiás" ),
    MA( "Maranhão" ),
    MT( "Mato Grosso" ),
    MS( "Mato Grosso do Sul" ),
    MG( "Minas Gerais" ),
    PA( "Pará" ),
    PB( "Paraíba" ),
    PR( "Paraná" ),
    PE( "Pernambuco" ),
    PI( "Piauí" ),
    RJ( "Rio de Janeiro" ),
    RN( "Rio Grande do Norte" ),
    RS( "Rio Grande do Sul" ),
    RO( "Rondonia" ),
    RR( "Roraima" ),
    SC( "Santa Catarina" ),
    SP( "São Paulo" ),
    SE( "Sergipe" ),
    TO( "Tocantins" );

    final private String stateName;

    Estado( String stateName ) {
        this.stateName = stateName;
    }

    public String getStateName( ) {
        return stateName;
    }

}
