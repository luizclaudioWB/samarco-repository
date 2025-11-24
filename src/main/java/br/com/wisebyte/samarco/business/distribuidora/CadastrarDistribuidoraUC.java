package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CadastrarDistribuidoraUC {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    @Inject
    DistribuidoraValidationBusiness validator;

    public DistribuidoraDTO cadastrar( DistribuidoraDTO dto ) {
        if ( validator.idIsNotNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora Id", "Id da distribuidora deve ser nulo" );
        }
        return distribuidoraMapper.toDTO( distribuidoraRepository.save( distribuidoraMapper.toEntity( dto ) ) );
    }
}
