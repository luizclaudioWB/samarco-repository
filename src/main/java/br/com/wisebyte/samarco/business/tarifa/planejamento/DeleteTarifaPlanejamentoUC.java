package br.com.wisebyte.samarco.business.tarifa.planejamento;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteTarifaPlanejamentoUC {

    @Inject
    TarifaPlanejamentoRepository repository;

    @Inject
    TarifaPlanejamentoValidationBusiness validator;

    @Transactional
    public void delete(@NotNull TarifaPlanejamentoDTO dto) {

        // Validacao 1: ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Id",
                    "ID e obrigatorio para exclusao"
            );
        }

        // Validacao 2: TarifaPlanejamento deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Id",
                    "Tarifa de Planejamento nao encontrada"
            );
        }

        // Busca a entidade para verificar a revisao
        TarifaPlanejamento entity = repository.findById(dto.getId()).orElseThrow();

        // Validacao 3: Revisao nao pode estar finalizada
        if (entity.getRevisao() != null && entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas excluidas"
            );
        }

        // Validacao 4: Verificar se pode deletar (sem filhos)
        if (!validator.podeDeletar(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Filhos",
                    "Tarifa possui registros vinculados (aliquotas, tarifas). Exclua-os primeiro."
            );
        }

        // Exclusao
        repository.delete(entity);
    }
}
