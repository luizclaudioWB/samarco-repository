package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@ApplicationScoped
public class TarifaDistribuidoraValidationBusiness {

    @Inject
    TarifaDistribuidoraRepository tarifaRepository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    public boolean idIsNull(TarifaDistribuidoraDTO dto) {
        return dto.getId() == null;
    }

    public boolean exists(Long id) {
        return tarifaRepository.findById(id).isPresent();
    }

    public boolean tarifaPlanejamentoExists(Long id) {
        return id != null &&
                tarifaPlanejamentoRepository.findById(id).isPresent();
    }


    public boolean distribuidoraExists(Long id) {
        return id != null &&
                distribuidoraRepository.findById(id).isPresent();
    }


    public boolean revisaoNaoFinalizada(Long tarifaPlanejamentoId) {
        return tarifaPlanejamentoRepository.findById(tarifaPlanejamentoId)
                .map(tp -> tp.getRevisao() != null && !tp.getRevisao().isFinished())
                .orElse(false);
    }


    public boolean periodoValido(TarifaDistribuidoraDTO dto) {
        if (dto.getPeriodoInicial() == null || dto.getPeriodoFinal() == null) {
            return false;
        }
        return dto.getPeriodoInicial().isBefore(dto.getPeriodoFinal()) ||
                dto.getPeriodoInicial().isEqual(dto.getPeriodoFinal());
    }


    public boolean valoresPositivos(TarifaDistribuidoraDTO dto) {
        return isPositiveOrZero(dto.getValorPonta()) &&
                isPositiveOrZero(dto.getValorForaPonta()) &&
                isPositiveOrZero(dto.getValorEncargos()) &&
                isPositiveOrZero(dto.getValorEncargosAutoProducao()) &&
                isPositiveOrZero(dto.getPercentualPisCofins());
    }

    private boolean isPositiveOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }


    public boolean icmsConsistente(TarifaDistribuidoraDTO dto) {
        if (!dto.isSobrescreverICMS()) {
            return true; // Nao vai usar, nao precisa validar
        }
        // Se vai sobrescrever, precisa ter valor valido
        return dto.getPercentualICMS() != null &&
                dto.getPercentualICMS().compareTo(BigDecimal.ZERO) >= 0 &&
                dto.getPercentualICMS().compareTo(BigDecimal.valueOf(100)) <= 0;
    }


    public boolean horasPontaValida(TarifaDistribuidoraDTO dto) {
        return dto.getQtdeDeHorasPonta() != null &&
                dto.getQtdeDeHorasPonta() > 0 &&
                dto.getQtdeDeHorasPonta() <= 744; // Maximo de horas em um mes
    }
}
