package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoProducaoMapper;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdatePlanejamentoProducaoUC {

    @Inject
    PlanejamentoProducaoRepository repository;

    @Inject
    PlanejamentoProducaoMapper mapper;

    @Inject
    PlanejamentoProducaoValidationBusiness validator;

    @Transactional
    public PlanejamentoProducaoDTO update(@NotNull PlanejamentoProducaoDTO dto) {
        validate( dto );

        PlanejamentoProducao atual = repository.findById( dto.getId( ) ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "PlanejamentoProducao", "Id", "PlanejamentoProducao nao encontrado" ) );
        atual.setValorJaneiro(dto.getValorJaneiro());
        atual.setValorFevereiro(dto.getValorFevereiro());
        atual.setValorMarco(dto.getValorMarco());
        atual.setValorAbril(dto.getValorAbril());
        atual.setValorMaio(dto.getValorMaio());
        atual.setValorJunho(dto.getValorJunho());
        atual.setValorJulho(dto.getValorJulho());
        atual.setValorAgosto(dto.getValorAgosto());
        atual.setValorSetembro(dto.getValorSetembro());
        atual.setValorOutubro(dto.getValorOutubro());
        atual.setValorNovembro(dto.getValorNovembro());
        atual.setValorDezembro(dto.getValorDezembro());
        PlanejamentoProducao saved = repository.save(atual);
        return mapper.toDTO(saved);
    }

    private void validate( PlanejamentoProducaoDTO dto ) {
        if ( !validator.exists( dto.getId( ) ) ) {
            throw new ValidadeExceptionBusiness( "PlanejamentoProducao", "Id", "Registro nao encontrado" );
        }
        PlanejamentoProducao atual = repository.findById( dto.getId( ) ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "PlanejamentoProducao", "Id", "PlanejamentoProducao nao encontrado" ) );
        if ( atual.getRevisao( ).isFinished( ) ) {
            throw new ValidadeExceptionBusiness( "PlanejamentoProducao", "Revisao", "Revisao finalizada nao pode ser alterada" );
        }
        if ( !validator.valoresMensaisValidos( dto ) ) {
            throw new ValidadeExceptionBusiness( "PlanejamentoProducao", "Valores Mensais", "Valores de producao nao podem ser negativos" );
        }
    }
}

