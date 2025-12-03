package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeletePlanejamentoProducaoUC {

    @Inject
    PlanejamentoProducaoRepository repository;

    @Inject
    PlanejamentoProducaoValidationBusiness validator;

    @Transactional
    public void delete(@NotNull PlanejamentoProducaoDTO dto) {

        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "PlanejamentoProducao",
                    "Id",
                    "Registro nao encontrado"
            );
        }

        PlanejamentoProducao entity = repository.findById(dto.getId())
                .orElseThrow();

        if (entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "PlanejamentoProducao",
                    "Revisao",
                    "Revisao finalizada nao pode ser alterada"
            );
        }

        repository.delete(entity);
    }
}