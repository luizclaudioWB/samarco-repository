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


    public boolean generatorUnitCannotBeNull( UnidadeDTO dto ) {
        boolean hasValue = dto.getGeraEnergia( ) != null;
        boolean geraEnergia = hasValue && (dto.getGeraEnergia( ).equals( Boolean.TRUE ));
        boolean hasUnidadeRecebedoraCreditosDeInjecao = geraEnergia && dto.getUnidadeRecebedoraCreditosDeInjecao( ) != null;
        boolean existsUnidadeRecebedoraCreditosDeInjecao = hasUnidadeRecebedoraCreditosDeInjecao && exists( dto.getUnidadeRecebedoraCreditosDeInjecao( ) );
        return !hasValue || !geraEnergia || (hasUnidadeRecebedoraCreditosDeInjecao && existsUnidadeRecebedoraCreditosDeInjecao);
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
        if ( dto.getUnidadeRecebedoraCreditosDeInjecao( ) == null ) {
            return false;
        }
        return !dto.getUnidadeRecebedoraCreditosDeInjecao( ).equals( dto.getId( ) );
    }

    public boolean unitHasAssociation( UnidadeDTO dto ) {
        Unidade unidade = unidadeRepository.findById( dto.getId( ) ).orElse( null );
        if ( null == unidade ) {
            return false;
        }
        return unidadeRepository.findByUnidadeRecebedoraCreditosDeInjecao( unidade ).isEmpty( );
    }

}
