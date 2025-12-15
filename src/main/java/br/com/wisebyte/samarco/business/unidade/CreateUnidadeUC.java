package br.com.wisebyte.samarco.business.unidade;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    @Inject
    UnidadeValidationBusiness validator;

    @Transactional
    public UnidadeDTO create( @NotNull UnidadeDTO dto ) {
        if ( !validator.cedenteUnitCannotBeNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Cadastro inválido", "Unidade cedente não existe" );
        }
        if (!validator.validateRedeBasicaDistribuidora(dto)) throw new ValidadeExceptionBusiness("Unidade", "Rede Básica", "Unidade conectada à rede básica não pode estar associada a uma distribuidora");
        if ( !validator.distribuidoraExists( dto.getDistribuidoraId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Distribuidora", "Distribuidora nao encontrada" );
        }
        if ( dto.getId( ) != null ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade Id deve ser nulo" );
        }
        Unidade entity = unidadeMapper.toEntity( dto );
        return unidadeMapper.toDTO( unidadeRepository.save( entity ) );
    }
}
