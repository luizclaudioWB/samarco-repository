package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.ConsumoEspecificoMapper;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateConsumoEspecificoUC {

    @Inject
    ConsumoEspecificoRepository consumoRepository;

    @Inject
    ConsumoEspecificoMapper consumoMapper;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public ConsumoEspecificoDTO create(@NotNull ConsumoEspecificoDTO dto) {
        if (!validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Id", "Id deve ser nulo para criação"
            );
        }

        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Revisão", "Revisão não encontrada"
            );
        }

        if (!validator.areaExists(dto.getAreaId())) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Área", "Área não encontrada"
            );
        }

        if (validator.isDuplicateKey(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Chave Única",
                "Já existe consumo específico para esta Área nesta Revisão"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Revisão",
                "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        return consumoMapper.toDTO(
            consumoRepository.save(consumoMapper.toEntity(dto))
        );
    }
}
