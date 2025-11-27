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
    EXCLUIR_USUARIO( "Permite excluir o usuario" );

    private final String descricao;

    Permissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDescricao( ) {
        return descricao;
    }
}
