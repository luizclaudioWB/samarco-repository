package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeleteUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeValidationBusiness validator;

    @Transactional
    public void delete( @NotNull UnidadeDTO dto ) {
        if ( !validator.exists( dto.getId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade não encontrada" );
        }
        if ( !validator.unitHasAssociation( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade está associada a outra entidade" );
        }
        unidadeRepository.deleteById( dto.getId( ) );
    }

}
