package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteTarifaFornecedorUC {

    @Inject
    TarifaFornecedorRepository repository;

    @Inject
    TarifaFornecedorValidationBusiness validator;

    @Transactional
    public void delete(@NotNull TarifaFornecedorDTO dto) {

        // 1. ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Id",
                    "ID e obrigatorio para exclusao"
            );
        }

        // 2. Tarifa deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Id",
                    "Tarifa de Fornecedor nao encontrada"
            );
        }

        // 3. Busca entidade
        TarifaFornecedor entity = repository.findById(dto.getId()).orElseThrow();

        // 4. Revisao nao pode estar finalizada
        if (entity.getPlanejamento() != null &&
                entity.getPlanejamento().getRevisao() != null &&
                entity.getPlanejamento().getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas excluidas"
            );
        }

        // 5. Exclusao
        repository.delete(entity);
    }
}
