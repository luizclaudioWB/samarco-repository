package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateDemandaUC {

    @Inject
    DemandaRepository demandaRepository;

    @Inject
    DemandaMapper demandaMapper;

    @Inject
    DemandaValidationBusiness validator;

    @Transactional
    public DemandaDTO create(@NotNull DemandaDTO dto) {
        if (!validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Id", "Id da Demanda deve ser nulo para criação"
            );
        }

        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Revisão", "Revisão não encontrada"
            );
        }

        if (!validator.unidadeExists(dto.getUnidadeId())) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Unidade", "Unidade não encontrada"
            );
        }

        if (validator.isDuplicateKey(dto)) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Chave Única",
                "Já existe demanda " + dto.getHorario().getDescricao() +
                " para esta Unidade nesta Revisão"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Revisão",
                "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        return demandaMapper.toDTO(
            demandaRepository.save(demandaMapper.toEntity(dto))
        );
    }
}
