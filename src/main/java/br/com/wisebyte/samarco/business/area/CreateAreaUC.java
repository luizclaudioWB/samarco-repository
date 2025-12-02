package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.mapper.area.AreaMapper;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateAreaUC {

    @Inject
    AreaRepository areaRepository;

    @Inject
    AreaMapper areaMapper;

    @Inject
    AreaValidatorBusiness validatorBusiness;

    @Transactional
    public AreaDTO create( @NotNull AreaDTO dto ) {
        if ( !validatorBusiness.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Area Id", "Id da Área deve ser nulo" );
        }

        if ( !validatorBusiness.unitExists( dto.getUnidadeId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Unidade", "Unidade não encontrada" );
        }

        if ( !validatorBusiness.userExists( dto.getUsuarioId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Usuario", "Usuário não encontrado" );
        }

        return areaMapper.toDTO( areaRepository.save( areaMapper.toEntity( dto ) ) );
    }
}
