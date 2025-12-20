package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class GeracaoValidationBusiness {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    public boolean idIsNull(GeracaoDTO dto) {
        return dto.getId() == null;
    }

    public boolean geracaoExists(GeracaoDTO dto) {
        if (dto.getId() == null) return false;
        return geracaoRepository.findById(dto.getId()).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean unidadeExists(Long unidadeId) {
        if (unidadeId == null) return false;
        return unidadeRepository.findById(unidadeId).isPresent();
    }

    public boolean isRevisaoFinished(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId)
            .map(Revisao::isFinished)
            .orElse(false);
    }

    public boolean isUnidadeGeradora(Long unidadeId) {
        if (unidadeId == null) return false;
        return unidadeRepository.findById(unidadeId)
            .map(Unidade::getUnidadeGeradora)
            .orElse(false);
    }

    public boolean isDuplicateKey(GeracaoDTO dto) {
        Optional<Revisao> revisaoOpt = revisaoRepository.findById(dto.getRevisaoId());
        Optional<Unidade> unidadeOpt = unidadeRepository.findById(dto.getUnidadeId());

        if (revisaoOpt.isEmpty() || unidadeOpt.isEmpty()) {
            return false;
        }

        var existing = geracaoRepository.findByRevisaoAndUnidade(
            revisaoOpt.get(),
            unidadeOpt.get()
        );

        if (existing.isEmpty()) {
            return false;
        }

        // Se existe mas é o mesmo ID, não é duplicata (é update)
        return !existing.get().getId().equals(dto.getId());
    }
}
