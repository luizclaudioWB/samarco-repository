package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class ExcluirUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeValidationBusiness validator;

    public void excluir( @NotNull UnidadeDTO dto ) {
        if ( !validator.validadeIdCannotBeNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Id da unidade não pode ser nulo" );
        }
        if ( !validator.validadeUnidadeWithAssociation( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade está associada a outra entidade" );
        }
        if ( !validator.existeUnidade( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade não encontrada" );
        }
        unidadeRepository.deleteById( dto.getId( ) );
    }

}
