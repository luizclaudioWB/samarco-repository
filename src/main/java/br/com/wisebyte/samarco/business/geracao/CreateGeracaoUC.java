package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.GeracaoMapper;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateGeracaoUC {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    GeracaoMapper geracaoMapper;

    @Inject
    GeracaoValidationBusiness validator;

    @Transactional
    public GeracaoDTO create(@NotNull GeracaoDTO dto) {

        if (!validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Id", "Id da Geração deve ser nulo para criação"
            );
        }

        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Revisão", "Revisão não encontrada"
            );
        }

        if (!validator.unidadeExists(dto.getUnidadeId())) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Unidade", "Unidade não encontrada"
            );
        }

        if (!validator.isUnidadeGeradora(dto.getUnidadeId())) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Unidade", "Apenas unidades geradoras podem ter registro de geração"
            );
        }

        if (validator.isDuplicateKey(dto)) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Chave Única", "Já existe geração para esta unidade nesta revisão"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Revisão", "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        return geracaoMapper.toDTO(
            geracaoRepository.save(geracaoMapper.toEntity(dto))
        );
    }
}
