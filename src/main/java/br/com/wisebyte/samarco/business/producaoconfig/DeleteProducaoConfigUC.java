package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeleteProducaoConfigUC {

    @Inject
    ProducaoConfigRepository repository;

    @Inject
    ProducaoConfigValidationBusiness validator;

    @Transactional
    public void delete(@NotNull ProducaoConfigDTO dto) {

        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Id",
                "Id nao pode ser nulo"
            );
        }

        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Id",
                "Configuracao de producao nao encontrada"
            );
        }

        ProducaoConfig entity = repository.findById(dto.getId()).orElseThrow();

        if (entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                "ProducaoConfig",
                "Revisao",
                "Revisao finalizada nao pode ser alterada"
            );
        }

        repository.delete(entity);
    }
}
