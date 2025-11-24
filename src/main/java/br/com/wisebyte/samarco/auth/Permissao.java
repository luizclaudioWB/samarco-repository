package br.com.wisebyte.samarco.auth;

public enum Permissao {
    CADASTRAR_UNIDADE( "Permite cadastrar a unidade" ),
    ALTERAR_UNIDADE( "Permite alterar a unidade" ),
    EXCLUIR_UNIDADE( "Permite excluir a unidade" ),
    CADASTRAR_DISTRIBUIDORA( "Permite cadastrar a distribuidora" ),
    ALTERAR_DISTRIBUIDORA( "Permite alterar a distribuidora" ),
    EXCLUIR_DISTRIBUIDORA( "Permite excluir a distribuidora" );

    private final String descricao;

    Permissao( String descricao ) {
        this.descricao = descricao;
    }

    public String getDescricao( ) {
        return descricao;
    }
}
