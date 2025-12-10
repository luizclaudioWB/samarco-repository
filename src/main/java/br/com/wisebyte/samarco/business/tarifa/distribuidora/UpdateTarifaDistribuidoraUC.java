package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaDistribuidoraMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateTarifaDistribuidoraUC {

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    TarifaDistribuidoraMapper mapper;

    @Inject
    TarifaDistribuidoraValidationBusiness validator;

    @Transactional
    public TarifaDistribuidoraDTO update(@NotNull TarifaDistribuidoraDTO dto) {

        // 1. ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Id",
                    "ID e obrigatorio para atualizacao"
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

        // 3. Busca entidade existente
        TarifaDistribuidora existing = repository.findById(dto.getId()).orElseThrow();

        // 4. Revisao atual nao pode estar finalizada
        if (existing.getPlanejamento() != null &&
                existing.getPlanejamento().getRevisao() != null &&
                existing.getPlanejamento().getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas alteradas"
            );
        }

        // 5. Periodo deve ser valido
        if (!validator.periodoValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Periodo",
                    "Periodo inicial deve ser anterior ou igual ao periodo final"
            );
        }

        // 6. Valores devem ser positivos
        if (!validator.valoresPositivos(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Valores",
                    "Todos os valores monetarios devem ser positivos ou zero"
            );
        }

        // 7. ICMS consistente
        if (!validator.icmsConsistente(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "ICMS",
                    "Se sobrescrever ICMS, deve informar percentual valido (0-100)"
            );
        }

        // 8. Horas de ponta validas
        if (!validator.horasPontaValida(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Horas Ponta",
                    "Quantidade de horas de ponta deve ser entre 1 e 744"
            );
        }

        // Aplica novos valores
        applyNewValues(existing, dto);

        TarifaDistribuidora saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    private void applyNewValues(TarifaDistribuidora entity, TarifaDistribuidoraDTO dto) {
        // Atualiza distribuidora se informada
        if (dto.getDistribuidoraId() != null) {
            entity.setDistribuidora(
                    distribuidoraRepository.findById(dto.getDistribuidoraId()).orElse(null)
            );
        }

        entity.setPeriodoInicial(dto.getPeriodoInicial());
        entity.setPeriodoFinal(dto.getPeriodoFinal());
        entity.setValorPonta(dto.getValorPonta());
        entity.setValorForaPonta(dto.getValorForaPonta());
        entity.setValorEncargos(dto.getValorEncargos());
        entity.setValorEncargosAutoProducao(dto.getValorEncargosAutoProducao());
        entity.setPercentualPisCofins(dto.getPercentualPisCofins());
        entity.setSobrescreverICMS(dto.isSobrescreverICMS());
        entity.setPercentualICMS(dto.getPercentualICMS());
        entity.setQtdeDeHorasPonta(dto.getQtdeDeHorasPonta());
    }
}
