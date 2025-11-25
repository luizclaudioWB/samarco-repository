package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class UpdateUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    @Inject
    UnidadeValidationBusiness validator;

    public UnidadeDTO update( @NotNull UnidadeDTO dto ) {
        if ( !validator.validadeIdCannotBeNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Id da unidade não pode ser nulo" );
        }
        if ( !validator.idsCannotBeEqual( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Geradora", "Id da unidade geradora não pode ser igual ao id da unidade" );
        }
        if ( !validator.unidadeGeradoraCannotBeNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Geradora", "Id da unidade geradora não pode ser nulo" );
        }
        if ( !validator.exists( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade não encontrada" );
        }
        Unidade entity = unidadeRepository.findById( dto.getId( ) ).orElseThrow( );
        applyNewValues( entity, dto );
        return unidadeMapper.toDTO( unidadeRepository.save( entity ) );
    }

    private void applyNewValues( Unidade unidade, @NotNull UnidadeDTO dto ) {
        unidade.setNomeUnidade( dto.getNome( ) );
        unidade.setEstado( dto.getEstado( ) );
        unidade.setConectadaRedeBasica( dto.getConectadaRedeBasica( ) );
        unidade.setUnidadeGeradora( dto.getGeraEnergia( ) );
        if ( unidade.getUnidadeGeradora( ) != null && unidade.getUnidadeGeradora( ) ) {
            unidadeRepository.findById( dto.getUnidadeRecebedoraCreditosDeInjecao( ) )
                    .ifPresent( unidade::setUnidadeRecebedoraCreditosDeInjecao );
        }
    }

}
