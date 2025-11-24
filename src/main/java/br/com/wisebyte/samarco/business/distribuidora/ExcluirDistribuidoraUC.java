package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ExcluirDistribuidoraUC {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    @Inject
    DistribuidoraValidationBusiness validator;

    public void excluir( DistribuidoraDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora Id", "Id da distribuidora não deve ser nulo" );
        }
        if ( !validator.existeDistribuidora( dto ) ) {
            throw new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora Id", "Distribuidora não encontrada" );
        }
        distribuidoraRepository.deleteById( dto.getId( ) );
    }
}
