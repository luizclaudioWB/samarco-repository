package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateDemandaUC {

    @Inject
    DemandaRepository demandaRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    DemandaMapper demandaMapper;

    @Inject
    DemandaValidationBusiness validator;

    @Transactional
    public DemandaDTO update(DemandaDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Id", "Id da Demanda não deve ser nulo"
            );
        }

        if (!validator.demandaExists(dto)) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Id", "Demanda não encontrada"
            );
        }

        if (validator.isDuplicateKey(dto)) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Chave Única",
                "Já existe outra demanda " + dto.getHorario().getDescricao() +
                " para esta Unidade nesta Revisão"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Revisão",
                "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        Demanda demanda = demandaRepository.findById(dto.getId()).orElseThrow();
        applyNewValues(demanda, dto);
        return demandaMapper.toDTO(demandaRepository.save(demanda));
    }

    private void applyNewValues(Demanda entity, DemandaDTO dto) {
        entity.setRevisao(
            dto.getRevisaoId() != null
                ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
                : null
        );
        entity.setUnidade(
            dto.getUnidadeId() != null
                ? unidadeRepository.findById(dto.getUnidadeId()).orElse(null)
                : null
        );
        entity.setHorario(dto.getHorario());
        entity.setValorJaneiro(dto.getValorJaneiro());
        entity.setValorFevereiro(dto.getValorFevereiro());
        entity.setValorMarco(dto.getValorMarco());
        entity.setValorAbril(dto.getValorAbril());
        entity.setValorMaio(dto.getValorMaio());
        entity.setValorJunho(dto.getValorJunho());
        entity.setValorJulho(dto.getValorJulho());
        entity.setValorAgosto(dto.getValorAgosto());
        entity.setValorSetembro(dto.getValorSetembro());
        entity.setValorOutubro(dto.getValorOutubro());
        entity.setValorNovembro(dto.getValorNovembro());
        entity.setValorDezembro(dto.getValorDezembro());
    }
}
