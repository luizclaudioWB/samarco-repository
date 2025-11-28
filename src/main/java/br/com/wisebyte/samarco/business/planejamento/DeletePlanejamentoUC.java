package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;


@ApplicationScoped
public class DeletePlanejamentoUC {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoValidationBusiness validator;

    @Transactional
    public void delete(@NotNull PlanejamentoDTO dto) {

        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Planejamento Id",
                    "Id do Planejamento não deve ser nulo para exclusão"
            );
        }

        if (!validator.existePlanejamento(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Planejamento Id",
                    "Planejamento não encontrado"
            );
        }

        planejamentoRepository.deleteById(dto.getId());
    }
}
