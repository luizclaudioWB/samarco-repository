package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.mapper.area.AreaMapper;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateAreaUC {

    @Inject
    AreaRepository areaRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    AreaMapper areaMapper;

    @Inject
    AreaValidatorBusiness validator;

    @Transactional
    public AreaDTO update( AreaDTO dto ) {
        if ( validator.idIsNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Area Id", "Id da Área não deve ser nulo" );
        }

        if ( !validator.areaExists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Area Id", "Área não encontrada" );
        }

        if ( !validator.unitExists( dto.getUnidadeId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Unidade", "Unidade não encontrada" );
        }

        if ( !validator.userExists( dto.getUsuarioId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Area", "Usuario", "Usuário não encontrado" );
        }

        Area area = areaRepository.findById( dto.getId( ) ).orElseThrow( );
        applyNewValues( area, dto );
        return areaMapper.toDTO( areaRepository.save( area ) );
    }

    private void applyNewValues( Area area, AreaDTO dto ) {
        area.setNomeArea( dto.getNomeArea( ) );
        area.setTipoArea( dto.getTipoArea( ) );
        area.setAtivo( dto.isAtivo( ) );
        area.setUnidade(
                dto.getUnidadeId( ) != null
                        ? unidadeRepository.findById( dto.getUnidadeId( ) ).orElse( null )
                        : null
        );
        area.setUsuario(
                dto.getUsuarioId( ) != null
                        ? usuarioRepository.findById( dto.getUsuarioId( ) ).orElse( null )
                        : null
        );
    }
}
