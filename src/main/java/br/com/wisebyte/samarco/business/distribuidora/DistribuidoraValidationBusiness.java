package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DistribuidoraValidationBusiness {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    public boolean idIsNull( DistribuidoraDTO dto ) {
        return dto.getId( ) == null;
    }

    public boolean existeDistribuidora( DistribuidoraDTO dto ) {
        return distribuidoraRepository.findById( dto.getId( ) ).isPresent( );
    }

}
