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

    public boolean validadeIdCannotBeNull( UnidadeDTO dto ) {
        return dto.getId( ) != null;
    }

    public boolean validadeUnidadeGeradoraCannotBeNull( UnidadeDTO dto ) {
        return dto.getGeraEnergia( ) != null && (
                dto.getGeraEnergia( ).equals( Boolean.FALSE ) ||
                        dto.getGeraEnergia( ).equals( Boolean.TRUE ) &&
                                dto.getUnidadeRecebedoraCreditosDeInjecao( ) != null &&
                                validadeExistsUnidadeGeradora( dto ));
    }

    public boolean validadeExistsUnidadeGeradora( UnidadeDTO dto ) {
        return unidadeRepository.findById( dto.getUnidadeRecebedoraCreditosDeInjecao( ) ).orElse( null ) != null;
    }

    public boolean validadeUnidadesIdsCannotBeEqual( UnidadeDTO dto ) {
        return dto.getGeraEnergia( ) != null && (
                dto.getGeraEnergia( ).equals( Boolean.FALSE ) ||
                        dto.getGeraEnergia( ).equals( Boolean.TRUE ) &&
                                !dto.getUnidadeRecebedoraCreditosDeInjecao( ).equals( dto.getId( ) ));
    }

    public boolean validadeUnidadeWithAssociation( UnidadeDTO dto ) {
        Unidade unidade = unidadeRepository.findById( dto.getUnidadeRecebedoraCreditosDeInjecao( ) ).orElse( null );
        if ( null == unidade ) {
            return false;
        }
        return unidadeRepository.findByUnidadeRecebedoraCreditosDeInjecao( unidade ).isEmpty( );
    }

    public boolean existeUnidade( UnidadeDTO dto ) {
        return unidadeRepository.findById( dto.getId( ) ).isPresent( );
    }
}
