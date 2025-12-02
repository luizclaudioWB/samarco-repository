package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateDistribuidoraUC {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    @Inject
    DistribuidoraValidationBusiness validator;

    @Transactional
    public DistribuidoraDTO update( DistribuidoraDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora Id", "Id da distribuidora não deve ser nulo" );
        }
        if ( !validator.distributorExists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora Id", "Distribuidora não encontrada" );
        }
        Distribuidora distribuidora = distribuidoraRepository.findById( dto.getId( ) ).orElseThrow( );
        applyNewValues( distribuidora, dto );
        return distribuidoraMapper.toDTO( distribuidoraRepository.save( distribuidora ) );
    }

    private void applyNewValues( Distribuidora distribuidora, DistribuidoraDTO dto ) {
        distribuidora.setNome( dto.getNome( ) );
        distribuidora.setCnpj( dto.getCnpj( ) );
        distribuidora.setSiglaAgente( dto.getSiglaAgente( ) );
        distribuidora.setEstado( dto.getEstado( ) );
    }
}
