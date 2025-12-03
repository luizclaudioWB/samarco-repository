package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;

@ApplicationScoped
public class TarifaFornecedorValidationBusiness {

    @Inject
    TarifaFornecedorRepository tarifaFornecedorRepository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    public boolean idIsNull(TarifaFornecedorDTO dto) {
        return dto == null || dto.getId() == null;
    }

    public boolean exists(Long id) {
        if (id == null) return false;
        return tarifaFornecedorRepository.findById(id).isPresent();
    }

    public boolean tarifaPlanejamentoExists(Long tarifaPlanejamentoId) {
        if (tarifaPlanejamentoId == null) return false;
        return tarifaPlanejamentoRepository.findById(tarifaPlanejamentoId).isPresent();
    }

    public boolean fornecedorExists(Long fornecedorId) {
        if (fornecedorId == null) return false;
        return fornecedorRepository.findById(fornecedorId).isPresent();
    }


    public boolean revisaoNaoFinalizada(Long tarifaPlanejamentoId) {
        if (tarifaPlanejamentoId == null) return true;

        return tarifaPlanejamentoRepository.findById(tarifaPlanejamentoId)
                .map(TarifaPlanejamento::getRevisao)
                .map(revisao -> !revisao.isFinished())
                .orElse(true);
    }


    public boolean ipcaValido(TarifaFornecedorDTO dto) {
        if (dto == null) return false;

        // IPCA realizada
        if (dto.getIpcaRealizada() != null &&
                dto.getIpcaRealizada().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // IPCA projetado
        if (dto.getIpcaProjetado() != null &&
                dto.getIpcaProjetado().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return true;
    }

    public boolean montanteValido(TarifaFornecedorDTO dto) {
        if (dto == null) return false;

        if (dto.getMontante() == null) {
            return false;
        }

        return dto.getMontante().compareTo(BigDecimal.ZERO) > 0;
    }


    public boolean camposObrigatoriosPreenchidos(TarifaFornecedorDTO dto) {
        if (dto == null) return false;

        return dto.getTarifaPlanejamentoId() != null
                && dto.getFornecedorId() != null
                && dto.getIpcaRealizada() != null
                && dto.getIpcaProjetado() != null
                && dto.getMontante() != null;
    }
}
