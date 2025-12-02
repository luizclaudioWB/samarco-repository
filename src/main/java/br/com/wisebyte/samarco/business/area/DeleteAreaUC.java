package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteAreaUC {

    @Inject
    AreaRepository areaRepository;

    @Inject
    AreaValidatorBusiness validator;

    @Transactional
    public void delete( AreaDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Area Id", "Id da Área não deve ser nulo" );
        }

        if ( !validator.areaExists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Area Id", "Área não encontrada" );
        }

        areaRepository.deleteById( dto.getId( ) );
    }
}
