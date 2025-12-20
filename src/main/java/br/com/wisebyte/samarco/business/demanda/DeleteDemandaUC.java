package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteDemandaUC {

    @Inject
    DemandaRepository demandaRepository;

    @Inject
    DemandaValidationBusiness validator;

    @Transactional
    public void delete(DemandaDTO dto) {
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

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Demanda", "Revisão",
                "Não é possível excluir demandas de uma revisão finalizada"
            );
        }

        demandaRepository.deleteById(dto.getId());
    }
}
