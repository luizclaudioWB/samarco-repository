package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class DemandaValidationBusiness {

    @Inject
    DemandaRepository demandaRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    public boolean idIsNull(DemandaDTO dto) {
        return dto.getId() == null;
    }

    public boolean demandaExists(DemandaDTO dto) {
        if (dto.getId() == null) return false;
        return demandaRepository.findById(dto.getId()).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean unidadeExists(Long unidadeId) {
        if (unidadeId == null) return false;
        return unidadeRepository.findById(unidadeId).isPresent();
    }

    public boolean isDuplicateKey(DemandaDTO dto) {
        Optional<Revisao> revisaoOpt = revisaoRepository.findById(dto.getRevisaoId());
        Optional<Unidade> unidadeOpt = unidadeRepository.findById(dto.getUnidadeId());

        if (revisaoOpt.isEmpty() || unidadeOpt.isEmpty()) {
            return false;
        }

        var existing = demandaRepository.findByRevisaoAndUnidadeAndHorario(
            revisaoOpt.get(),
            unidadeOpt.get(),
            dto.getHorario()
        );

        if (existing.isEmpty()) {
            return false;
        }

        return !existing.get().getId().equals(dto.getId());
    }

    public boolean isRevisaoFinished(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId)
            .map(Revisao::isFinished)
            .orElse(false);
    }
}
