package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class AliquotaImpostosValidationBusiness {

    @Inject
    AliquotaImpostosRepository aliquotaRepository;

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;


    public boolean idIsNull(AliquotaImpostosDTO dto) {
        return dto.getId() == null;
    }


    public boolean exists(Long id) {
        return aliquotaRepository.findById(id).isPresent();
    }


    public boolean tarifaPlanejamentoExists(Long tarifaPlanejamentoId) {
        return tarifaPlanejamentoId != null &&
                tarifaPlanejamentoRepository.findById(tarifaPlanejamentoId).isPresent();
    }


    public boolean combinacaoUnica(AliquotaImpostosDTO dto) {
        TarifaPlanejamento planejamento = tarifaPlanejamentoRepository
                .findById(dto.getTarifaPlanejamentoId())
                .orElse(null);

        if (planejamento == null) {
            return true; // Vai falhar em outra validacao
        }

        Optional<AliquotaImpostos> existente = aliquotaRepository
                .findByPlanejamentoAndAnoAndEstado(
                        planejamento,
                        dto.getAno(),
                        dto.getEstado()
                );

        // Se nao existe, e unico
        if (existente.isEmpty()) return true;

        // Se existe, verifica se e o mesmo registro (update)
        return existente.get().getId().equals(dto.getId());
    }


    public boolean percentuaisValidos(AliquotaImpostosDTO dto) {
        return isValidPercentual(dto.getPercentualPis()) &&
                isValidPercentual(dto.getPercentualCofins()) &&
                isValidPercentual(dto.getPercentualIcms()) &&
                isValidPercentual(dto.getPercentualIpca());
    }


    private boolean isValidPercentual(BigDecimal valor) {
        if (valor == null) return false;
        return valor.compareTo(BigDecimal.ZERO) >= 0 &&
                valor.compareTo(BigDecimal.valueOf(100)) <= 0;
    }


    public boolean anoValido(AliquotaImpostosDTO dto) {
        return dto.getAno() != null &&
                dto.getAno() >= 2020 &&
                dto.getAno() <= 2100;
    }


    public boolean estadoInformado(AliquotaImpostosDTO dto) {
        return dto.getEstado() != null;
    }


    public boolean revisaoNaoFinalizada(Long tarifaPlanejamentoId) {
        return tarifaPlanejamentoRepository.findById(tarifaPlanejamentoId)
                .map(tp -> tp.getRevisao() != null && !tp.getRevisao().isFinished())
                .orElse(false);
    }
}
