package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FornecedorValidatorBusiness {

    @Inject
    FornecedorRepository fornecedorRepository;

    public boolean idIsNull( FornecedorDTO dto ) {
        return dto.getId( ) == null;
    }

    public boolean existeFornecedor( FornecedorDTO dto ) {
        return fornecedorRepository.findById( dto.getId( ) ).isPresent( );
    }


}
