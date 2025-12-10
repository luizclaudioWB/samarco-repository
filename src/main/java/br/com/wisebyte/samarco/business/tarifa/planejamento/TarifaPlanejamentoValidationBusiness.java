package br.com.wisebyte.samarco.business.tarifa.planejamento;

import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class TarifaPlanejamentoValidationBusiness {

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    RevisaoRepository revisaoRepository;


    public boolean idIsNull(TarifaPlanejamentoDTO dto) {
        return dto.getId() == null;
    }


    public boolean exists(Long id) {
        return tarifaPlanejamentoRepository.findById(id).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        return revisaoId != null &&
                revisaoRepository.findById(revisaoId).isPresent();
    }


    public boolean revisaoNaoFinalizada(Long revisaoId) {
        return revisaoRepository.findById(revisaoId)
                .map(revisao -> !revisao.isFinished())
                .orElse(false);
    }


    public boolean revisaoUnica(TarifaPlanejamentoDTO dto) {
        Revisao revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElse(null);

        if (revisao == null) {
            return true; // Vai falhar em outra validacao (revisaoExists)
        }

        Optional<TarifaPlanejamento> existente = tarifaPlanejamentoRepository
                .findByRevisao(revisao);

        // Se nao existe nenhuma para esta revisao, e unico
        if (existente.isEmpty()) {
            return true;
        }

        // Se existe, verifica se e o mesmo registro (caso de UPDATE)
        // Se for o mesmo ID, pode continuar (esta atualizando a propria)
        return existente.get().getId().equals(dto.getId());
    }

    public boolean podeDeletar(Long id) {
        // Futuramente verificar se tem AliquotaImpostos, TarifaDistribuidora, etc
        // Por enquanto, permite exclusao
        return true;
    }
}
