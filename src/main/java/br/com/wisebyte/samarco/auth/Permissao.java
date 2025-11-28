package br.com.wisebyte.samarco.auth;

public enum Permissao {
    CADASTRAR_UNIDADE( "Permite cadastrar a unidade" ),
    ALTERAR_UNIDADE( "Permite alterar a unidade" ),
    EXCLUIR_UNIDADE( "Permite excluir a unidade" ),
    CADASTRAR_DISTRIBUIDORA( "Permite cadastrar a distribuidora" ),
    ALTERAR_DISTRIBUIDORA( "Permite alterar a distribuidora" ),
    EXCLUIR_DISTRIBUIDORA( "Permite excluir a distribuidora" ),
    CADASTRAR_FORNECEDOR( "Permite cadastrar o fornecedor" ),
    ALTERAR_FORNECEDOR( "Permite alterar o fornecedor" ),
    EXCLUIR_FORNECEDOR( "Permite excluir o fornecedor" ),
    CADASTRAR_USUARIO( "Permite cadastrar o usuario" ),
    ALTERAR_USUARIO( "Permite alterar o usuario" ),
    EXCLUIR_USUARIO( "Permite excluir o usuario" ),
    CADASTRAR_AREA( "Permite cadastrar área" ),
    ALTERAR_AREA( "Permite alterar área" ),
    EXCLUIR_AREA( "Permite excluir área" ),
    LISTAR_AREA( "Permite listar áreas" ),
    CADASTRAR_PLANEJAMENTO( "Permite cadastrar planejamento" ),
    ALTERAR_PLANEJAMENTO( "Permite alterar planejamento" ),
    EXCLUIR_PLANEJAMENTO( "Permite excluir planejamento" ),
    LISTAR_PLANEJAMENTO( "Permite listar planejamentos" ),
    CADASTRAR_REVISAO( "Permite cadastrar revisão" ),
    ALTERAR_REVISAO( "Permite alterar revisão" ),
    EXCLUIR_REVISAO( "Permite excluir revisão" ),
    LISTAR_REVISAO( "Permite listar revisões" ),
    FINALIZAR_REVISAO( "Permite finalizar revisão (tornar imutável)" );

    private final String descricao;

    Permissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDescricao( ) {
        return descricao;
    }
}
