package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateFornecedorUC {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    @Inject
    FornecedorValidatorBusiness validatorBusiness;

    public FornecedorDTO create( @NotNull FornecedorDTO dto ) {
        if ( validatorBusiness.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor Id", "Id do Fornecedor deve ser nulo" );
        }
        return fornecedorMapper.toDTO( fornecedorRepository.save( fornecedorMapper.toEntity( dto ) ) );
    }
}
