package br.com.wisebyte.samarco.business.tarifa.planejamento;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaPlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateTarifaPlanejamentoUC {

    @Inject
    TarifaPlanejamentoRepository repository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    TarifaPlanejamentoMapper mapper;

    @Inject
    TarifaPlanejamentoValidationBusiness validator;

    @Transactional
    public TarifaPlanejamentoDTO update(@NotNull TarifaPlanejamentoDTO dto) {

        // Validacao 1: ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Id",
                    "ID e obrigatorio para atualizacao"
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

        // Validacao 3: Nova Revisao deve existir
        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Revisao nao encontrada"
            );
        }

        // Busca a entidade existente para verificar a revisao atual
        TarifaPlanejamento existing = repository.findById(dto.getId()).orElseThrow();

        // Validacao 4: Revisao atual nao pode estar finalizada
        if (existing.getRevisao() != null && existing.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Revisao atual finalizada nao pode ter tarifas alteradas"
            );
        }

        // Validacao 5: Nova revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Nova revisao finalizada nao pode receber tarifas"
            );
        }

        // Validacao 6: Nova revisao deve ser unica (ou a mesma)
        if (!validator.revisaoUnica(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "A nova revisao ja possui uma Tarifa de Planejamento cadastrada"
            );
        }

        // Aplica novos valores
        existing.setRevisao(
                revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
        );

        TarifaPlanejamento saved = repository.save(existing);
        return mapper.toDTO(saved);
    }
}
