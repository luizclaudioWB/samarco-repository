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
    FINALIZE_REVISION( "Permite finalizar revisão (tornar imutável)" );

    private final String descricao;

    Permissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDescricao( ) {
        return descricao;
    }
}
