package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UpdateFornecedorUC {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    @Inject
    FornecedorValidatorBusiness validator;

    public FornecedorDTO update( FornecedorDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor Id", "Id do Fornecedor não deve ser nulo" );
        }
        if ( !validator.exists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor Id", "Fornecedor não encontrada" );
        }
        Fornecedor fornecedor = fornecedorRepository.findById( dto.getId( ) ).orElseThrow( );
        applyNewValues( fornecedor, dto );
        return fornecedorMapper.toDTO( fornecedorRepository.save( fornecedor ) );
    }

    private void applyNewValues( Fornecedor fornecedor, FornecedorDTO dto ) {
        fornecedor.setNome( dto.getNome( ) );
        fornecedor.setCnpj( dto.getCnpj( ) );
        fornecedor.setEstado( dto.getEstado( ) );
        fornecedor.setInicioDatabase( dto.getInicioDatabase( ) );
        fornecedor.setPrecoBase( dto.getPrecoBase( ) );
    }
}
