package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.ConsumoEspecificoMapper;
import br.com.wisebyte.samarco.model.consumo.ConsumoEspecifico;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateConsumoEspecificoUC {

    @Inject
    ConsumoEspecificoRepository consumoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Inject
    ConsumoEspecificoMapper consumoMapper;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public ConsumoEspecificoDTO update(ConsumoEspecificoDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Id", "Id não deve ser nulo"
            );
        }

        if (!validator.consumoExists(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Id", "Consumo específico não encontrado"
            );
        }

        if (validator.isDuplicateKey(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Chave Única",
                "Já existe outro consumo específico para esta Área nesta Revisão"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Revisão",
                "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        ConsumoEspecifico consumo = consumoRepository.findById(dto.getId()).orElseThrow();
        applyNewValues(consumo, dto);
        return consumoMapper.toDTO(consumoRepository.save(consumo));
    }

    private void applyNewValues(ConsumoEspecifico entity, ConsumoEspecificoDTO dto) {
        entity.setRevisao(
            dto.getRevisaoId() != null
                ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
                : null
        );
        entity.setArea(
            dto.getAreaId() != null
                ? areaRepository.findById(dto.getAreaId()).orElse(null)
                : null
        );
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
