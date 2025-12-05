package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteRevisaoUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    RevisaoValidationBusiness validator;

    @Transactional
    public void delete( RevisaoDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Revisao Id", "Id da Revisão não deve ser nulo" );
        }

        if ( !validator.existeRevisao( dto ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Revisao Id", "Revisão não encontrada" );
        }

        revisaoRepository.deleteById( dto.getId( ) );
    }
}
