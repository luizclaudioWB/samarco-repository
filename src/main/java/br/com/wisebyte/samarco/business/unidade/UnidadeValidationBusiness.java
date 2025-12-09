package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UnidadeValidationBusiness {

    @Inject
    UnidadeRepository unidadeRepository;


    public boolean cedenteUnitCannotBeNull( UnidadeDTO dto ) {
        boolean hasUnidadeRecebedoraCreditosDeInjecao = dto.getUnidadeCedenteCreditosDeInjecao( ) != null;
        boolean existsUnidadeCedenteCreditosDeInjecao = hasUnidadeRecebedoraCreditosDeInjecao && exists( dto.getUnidadeCedenteCreditosDeInjecao( ) );
        return !hasUnidadeRecebedoraCreditosDeInjecao || existsUnidadeCedenteCreditosDeInjecao;
    }

    public boolean exists( Long id ) {
        if ( id == null ) {
            return false;
        }
        return unidadeRepository.findById( id ).isPresent( );
    }

    public boolean idsCannotBeEqual( UnidadeDTO dto ) {
        if ( dto.getGeraEnergia( ) == null ) {
            return false;
        }
        if ( dto.getGeraEnergia( ).equals( Boolean.FALSE ) ) {
            return true;
        }
        // Se geraEnergia é true, precisa verificar se unidadeRecebedoraCreditosDeInjecao não é null
        // e se não é igual ao próprio id
        if ( dto.getUnidadeCedenteCreditosDeInjecao( ) == null ) {
            return false;
        }
        return !dto.getUnidadeCedenteCreditosDeInjecao( ).equals( dto.getId( ) );
    }

    public boolean unitHasAssociation( UnidadeDTO dto ) {
        Unidade unidade = unidadeRepository.findById( dto.getId( ) ).orElse( null );
        if ( null == unidade ) {
            return false;
        }
        return unidadeRepository.findByUnidadeCedenteCreditosDeInjecao( unidade ).isEmpty( );
    }

}
