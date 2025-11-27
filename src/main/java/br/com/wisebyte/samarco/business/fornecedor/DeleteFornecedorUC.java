package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteFornecedorUC {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorValidatorBusiness validator;

    @Transactional
    public void delete( FornecedorDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor Id", "Id do Fornecedor não deve ser nulo" );
        }
        if ( !validator.existeFornecedor( dto ) ) {
            throw new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor Id", "Fornecedor não encontrado" );
        }
        fornecedorRepository.deleteById( dto.getId( ) );
    }

}
