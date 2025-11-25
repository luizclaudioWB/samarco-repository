package br.com.wisebyte.samarco.mapper.fornecedor;

import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorMapper {

    public Fornecedor toEntity( FornecedorDTO dto ) {
        return Fornecedor.builder( )
                .nome( dto.getNome( ) )
                .cnpj( dto.getCnpj( ) )
                .inicioDatabase( dto.getInicioDatabase( ) )
                .precoBase( dto.getPrecoBase( ) )
                .estado( dto.getEstado( ) )
                .build( );
    }

    public FornecedorDTO toDTO( Fornecedor entity ) {
        return FornecedorDTO.builder( )
                .nome( entity.getNome( ) )
                .cnpj( entity.getCnpj( ) )
                .inicioDatabase( entity.getInicioDatabase( ) )
                .precoBase( entity.getPrecoBase( ) )
                .estado( entity.getEstado( ) )
                .build( );
    }
}
