package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.model.geracao.Geracao;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class GeracaoValidationBusiness {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    public Geracao validateExists(Long id) {
        return geracaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                "Geração não encontrada com o ID: " + id
            ));
    }

    public Revisao validateRevisaoExists(Long revisaoId) {
        return revisaoRepository.findById(revisaoId)
            .orElseThrow(() -> new NotFoundException(
                "Revisão não encontrada com o ID: " + revisaoId
            ));
    }

    public Unidade validateUnidadeExists(Long unidadeId) {
        return unidadeRepository.findById(unidadeId)
            .orElseThrow(() -> new NotFoundException(
                "Unidade não encontrada com o ID: " + unidadeId
            ));
    }

    public void validateRevisaoNotFinished(Revisao revisao) {
        if (revisao.isFinished()) {
            throw new BadRequestException(
                "Não é possível modificar geração de uma revisão finalizada"
            );
        }
    }

    public void validateUnidadeIsGeradora(Unidade unidade) {
        if (!unidade.getUnidadeGeradora()) {
            throw new BadRequestException(
                "A unidade '" + unidade.getNome() + "' não é uma unidade geradora. " +
                "Apenas unidades com unidadeGeradora=true podem ter registros de geração."
            );
        }
    }

    public void validateUniqueRevisaoUnidade(GeracaoDTO dto) {
        Optional<Geracao> existing = geracaoRepository.findByRevisaoAndUnidade(
            revisaoRepository.findById(dto.getRevisaoId()).orElse(null),
            unidadeRepository.findById(dto.getUnidadeId()).orElse(null)
        );

        if (existing.isPresent() && !existing.get().getId().equals(dto.getId())) {
            throw new BadRequestException(
                "Já existe um registro de geração para esta revisão e unidade"
            );
        }
    }

    public void validateForCreate(GeracaoDTO dto) {
        Revisao revisao = validateRevisaoExists(dto.getRevisaoId());
        Unidade unidade = validateUnidadeExists(dto.getUnidadeId());

        validateRevisaoNotFinished(revisao);
        validateUnidadeIsGeradora(unidade);
        validateUniqueRevisaoUnidade(dto);
    }

    public void validateForUpdate(GeracaoDTO dto) {
        validateExists(dto.getId());
        Revisao revisao = validateRevisaoExists(dto.getRevisaoId());
        Unidade unidade = validateUnidadeExists(dto.getUnidadeId());

        validateRevisaoNotFinished(revisao);
        validateUnidadeIsGeradora(unidade);
        validateUniqueRevisaoUnidade(dto);
    }

    public void validateForDelete(Long id) {
        Geracao geracao = validateExists(id);
        validateRevisaoNotFinished(geracao.getRevisao());
    }
}
