package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeleteDemandaUC {

    @Inject
    DemandaRepository repository;

    @Transactional
    public void delete(@NotNull DemandaDTO dto) {
        if (dto.getId() == null) {
            throw new ValidadeExceptionBusiness("Demanda", "Id", "Id nao pode ser nulo para exclusao");
        }

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("Demanda", "Id", "Demanda nao encontrada"));

        if (entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("Demanda", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        repository.delete(entity);
    }
}
