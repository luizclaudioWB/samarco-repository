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
public class CadastrarUnidadeUC {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    @Inject
    UnidadeValidationBusiness validator;

    public UnidadeDTO cadastrar( @NotNull UnidadeDTO dto ) {
        if ( !validator.validadeUnidadeGeradoraCannotBeNull( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Cadastro inválido", "Erro nos dados informados para cadastro" );
        }
        if ( validator.existeUnidade( dto ) ) {
            throw new ValidadeExceptionBusiness( "Unidade", "Unidade Id", "Unidade já cadastrada" );
        }
        Unidade entity = unidadeMapper.toEntity( dto );
        return unidadeMapper.toDTO( unidadeRepository.save( entity ) );
    }
}
