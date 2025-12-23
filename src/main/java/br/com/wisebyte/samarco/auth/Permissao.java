package br.com.wisebyte.samarco.auth;

public enum Permissao {


    CREATE_UNIT( "Permite cadastrar a unidade" ),
    UPDATE_UNIT( "Permite alterar a unidade" ),
    DELETE_UNIT( "Permite excluir a unidade" ),
    LIST_UNITS( "Permite listar todas as unidades" ),
    GET_UNIT_BY_ID( "Permite buscar unidade por ID" ),
    LIST_UNITS_BY_INJECTION_CREDIT_RECEIVER( "Permite listar unidades por recebedora de créditos de injeção" ),

    CREATE_DISTRIBUTOR( "Permite cadastrar a distribuidora" ),
    UPDATE_DISTRIBUTOR( "Permite alterar a distribuidora" ),
    DELETE_DISTRIBUTOR( "Permite excluir a distribuidora" ),
    LIST_DISTRIBUTORS( "Permite listar todas as distribuidoras" ),
    GET_DISTRIBUTOR_BY_ID( "Permite buscar distribuidora por ID" ),

    CREATE_SUPPLIER( "Permite cadastrar o fornecedor" ),
    UPDATE_SUPPLIER( "Permite alterar o fornecedor" ),
    DELETE_SUPPLIER( "Permite excluir o fornecedor" ),
    LIST_SUPPLIERS( "Permite listar todos os fornecedores" ),
    GET_SUPPLIER_BY_ID( "Permite buscar fornecedor por ID" ),

    CREATE_USER( "Permite cadastrar o usuário" ),
    UPDATE_USER( "Permite alterar o usuário" ),
    DELETE_USER( "Permite excluir o usuário" ),
    LIST_USERS( "Permite listar todos os usuários" ),
    GET_USER_BY_ID( "Permite buscar usuário por ID" ),
    GET_USER_BY_NAME( "Permite buscar usuário por nome" ),

    CREATE_AREA( "Permite cadastrar área" ),
    UPDATE_AREA( "Permite alterar área" ),
    DELETE_AREA( "Permite excluir área" ),
    LIST_AREAS( "Permite listar todas as áreas" ),
    GET_AREA_BY_ID( "Permite buscar área por ID" ),
    LIST_AREAS_BY_TYPE( "Permite listar áreas por tipo" ),
    LIST_ACTIVE_AREAS( "Permite listar áreas ativas" ),

    CREATE_PLANNING( "Permite cadastrar planejamento" ),
    UPDATE_PLANNING( "Permite alterar planejamento" ),
    DELETE_PLANNING( "Permite excluir planejamento" ),
    LIST_PLANNINGS( "Permite listar todos os planejamentos" ),
    GET_PLANNING_BY_ID( "Permite buscar planejamento por ID" ),
    GET_PLANNING_BY_YEAR( "Permite buscar planejamento por ano" ),
    GET_CURRENT_PLANNING( "Permite buscar planejamento corrente" ),

    CREATE_REVISION( "Permite cadastrar revisão" ),
    UPDATE_REVISION( "Permite alterar revisão" ),
    DELETE_REVISION( "Permite excluir revisão" ),
    LIST_REVISIONS( "Permite listar todas as revisões" ),
    GET_REVISION_BY_ID( "Permite buscar revisão por ID" ),
    LIST_OFFICIAL_REVISIONS( "Permite listar revisões oficiais" ),
    LIST_REVISIONS_BY_PLANNING( "Permite listar revisões por planejamento" ),
    FINALIZE_REVISION( "Permite finalizar revisão (tornar imutável)" ),

    CREATE_PRODUCTION_PLANNING("Permite cadastrar planejamento de producao"),
    UPDATE_PRODUCTION_PLANNING("Permite alterar planejamento de producao"),
    DELETE_PRODUCTION_PLANNING("Permite excluir planejamento de producao"),
    LIST_PRODUCTION_PLANNINGS("Permite listar todos os planejamentos de producao"),
    GET_PRODUCTION_PLANNING_BY_ID("Permite buscar planejamento de producao por ID"),
    LIST_PRODUCTION_PLANNINGS_BY_REVISION("Permite listar planejamentos de producao por revisao"),
    GET_PRODUCTION_PLANNING_BY_REVISION_AND_AREA("Permite buscar planejamento de producao por revisao e area"),

    LIST_PRODUCTION_CONFIGS("Permite listar configuracoes de producao"),
    GET_PRODUCTION_CONFIG_BY_ID("Permite buscar configuracao de producao por ID"),
    CREATE_PRODUCTION_CONFIG("Permite cadastrar configuracao de producao"),
    UPDATE_PRODUCTION_CONFIG("Permite alterar configuracao de producao"),
    DELETE_PRODUCTION_CONFIG("Permite excluir configuracao de producao"),

    CREATE_TAX_RATE("Permite cadastrar aliquota de impostos"),
    UPDATE_TAX_RATE("Permite alterar aliquota de impostos"),
    DELETE_TAX_RATE("Permite excluir aliquota de impostos"),
    LIST_TAX_RATES("Permite listar aliquotas de impostos"),
    GET_TAX_RATE_BY_ID("Permite buscar aliquota de impostos por ID"),
    LIST_TAX_RATES_BY_STATE("Permite listar aliquotas de impostos por estado"),

    CREATE_RATE_PLANNING("Permite cadastrar tarifa de planejamento"),
    UPDATE_RATE_PLANNING("Permite alterar tarifa de planejamento"),
    DELETE_RATE_PLANNING("Permite excluir tarifa de planejamento"),
    LIST_RATE_PLANNINGS("Permite listar tarifas de planejamento"),
    GET_RATE_PLANNING_BY_ID("Permite buscar tarifa de planejamento por ID"),
    GET_RATE_PLANNING_BY_REVISION("Permite buscar tarifa de planejamento por revisao"),

    CREATE_DISTRIBUTOR_RATE("Permite cadastrar tarifa de distribuidora"),
    UPDATE_DISTRIBUTOR_RATE("Permite alterar tarifa de distribuidora"),
    DELETE_DISTRIBUTOR_RATE("Permite excluir tarifa de distribuidora"),
    LIST_DISTRIBUTOR_RATES("Permite listar tarifas de distribuidora"),
    LIST_DISTRIBUTOR_TABLE( "Permite listar a tabela de tarifas de distribuidora" ),
    GET_DISTRIBUTOR_RATE_BY_ID("Permite buscar tarifa de distribuidora por ID"),

    CREATE_SUPPLIER_RATE("Permite cadastrar tarifa de fornecedor"),
    UPDATE_SUPPLIER_RATE("Permite alterar tarifa de fornecedor"),
    DELETE_SUPPLIER_RATE("Permite excluir tarifa de fornecedor"),
    LIST_SUPPLIER_RATES("Permite listar tarifas de fornecedor"),
    GET_SUPPLIER_RATE_BY_ID("Permite buscar tarifa de fornecedor por ID"),

    CREATE_SPECIFIC_CONSUMPTION("Permite cadastrar consumo especifico"),
    UPDATE_SPECIFIC_CONSUMPTION("Permite alterar consumo especifico"),
    DELETE_SPECIFIC_CONSUMPTION("Permite excluir consumo especifico"),
    LIST_SPECIFIC_CONSUMPTIONS("Permite listar consumos especificos"),
    GET_SPECIFIC_CONSUMPTION_BY_ID("Permite buscar consumo especifico por ID"),
    CALC_CONSUMPTION_AREA("Permite calcular consumo por area"),

    CREATE_DEMAND("Permite cadastrar demanda"),
    UPDATE_DEMAND("Permite alterar demanda"),
    DELETE_DEMAND("Permite excluir demanda"),
    LIST_DEMANDS("Permite listar demandas"),
    GET_DEMAND_BY_ID("Permite buscar demanda por ID"),

    CREATE_GENERATION_PLANNING("Permite cadastrar planejamento de geracao"),
    UPDATE_GENERATION_PLANNING("Permite alterar planejamento de geracao"),
    DELETE_GENERATION_PLANNING("Permite excluir planejamento de geracao"),
    LIST_GENERATION_PLANNINGS("Permite listar planejamentos de geracao"),
    GET_GENERATION_PLANNING_BY_ID("Permite buscar planejamento de geracao por ID"),

    CALC_ENERGY_COST("Permite calcular custo de energia");

    private final String descricao;

    Permissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDescricao( ) {
        return descricao;
    }
}
