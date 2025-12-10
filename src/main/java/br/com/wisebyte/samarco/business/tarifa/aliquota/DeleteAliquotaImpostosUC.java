package br.com.wisebyte.samarco.business.tarifa.aliquota;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class DeleteAliquotaImpostosUC {

    @Inject
    AliquotaImpostosRepository repository;

    @Inject
    AliquotaImpostosValidationBusiness validator;

    @Transactional
    public void delete(@NotNull AliquotaImpostosDTO dto) {

        // Validacao 1: ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Id",
                    "ID e obrigatorio para exclusao"
            );
        }

        // Validacao 2: Aliquota deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Id",
                    "Aliquota nao encontrada"
            );
        }

        // Busca a entidade para verificar a revisao
        AliquotaImpostos entity = repository.findById(dto.getId()).orElseThrow();

        // Validacao 3: Revisao nao pode estar finalizada
        if (entity.getPlanejamento() != null &&
                !validator.revisaoNaoFinalizada(entity.getPlanejamento().getId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Revisao",
                    "Revisao finalizada nao pode ser alterada"
            );
        }

        // Exclusao
        repository.delete(entity);
    }
}
