package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.mapper.producao.ProducaoConfigMapper;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateProducaoConfigUC {

    @Inject
    ProducaoConfigRepository repository;

    @Inject
    ProducaoConfigMapper mapper;

    @Inject
    ProducaoConfigValidationBusiness validator;

    @Transactional
    public ProducaoConfigDTO create(@NotNull ProducaoConfigDTO dto) {

        if (!validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Id",
                "Id deve ser nulo para criacao"
            );
        }

        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Revisao",
                "Revisao nao encontrada"
            );
        }

        if (!validator.revisaoNaoFinalizada(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Revisao",
                "Revisao finalizada nao pode ser alterada"
            );
        }

        if (!validator.revisaoUnica(dto)) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Revisao",
                "Ja existe uma configuracao de producao para esta revisao"
            );
        }

        if (!validator.todasAreasExistem(dto.getAreaIds())) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Areas",
                "Uma ou mais areas nao foram encontradas"
            );
        }

        if (!validator.todasAreasAtivas(dto.getAreaIds())) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Areas",
                "Uma ou mais areas estao desativadas"
            );
        }

        ProducaoConfig entity = mapper.toEntity(dto);
        ProducaoConfig saved = repository.save(entity);
        return mapper.toDTO(saved);
    }
}
