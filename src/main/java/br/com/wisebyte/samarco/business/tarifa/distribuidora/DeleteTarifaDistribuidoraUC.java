package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteTarifaDistribuidoraUC {

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaDistribuidoraValidationBusiness validator;

    @Transactional
    public void delete(@NotNull TarifaDistribuidoraDTO dto) {

        // 1. ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Id",
                    "ID e obrigatorio para exclusao"
            );
        }

        // 2. Tarifa deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Id",
                    "Tarifa de Distribuidora nao encontrada"
            );
        }

        // 3. Busca entidade
        TarifaDistribuidora entity = repository.findById(dto.getId()).orElseThrow();

        // 4. Revisao nao pode estar finalizada
        if (entity.getPlanejamento() != null &&
                entity.getPlanejamento().getRevisao() != null &&
                entity.getPlanejamento().getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas excluidas"
            );
        }

        // Exclusao
        repository.delete(entity);
    }
}
