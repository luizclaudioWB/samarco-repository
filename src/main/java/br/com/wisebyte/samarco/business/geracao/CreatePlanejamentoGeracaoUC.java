package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.PlanejamentoGeracaoMapper;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreatePlanejamentoGeracaoUC {

    @Inject
    PlanejamentoGeracaoRepository repository;

    @Inject
    PlanejamentoGeracaoMapper mapper;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Transactional
    public PlanejamentoGeracaoDTO create(@NotNull PlanejamentoGeracaoDTO dto) {
        validate(dto);
        var entity = mapper.toEntity(dto);
        var saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    private void validate(PlanejamentoGeracaoDTO dto) {
        if (dto.getId() != null) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Id deve ser nulo para criacao");
        }

        var revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("PlanejamentoGeracao", "Revisao", "Revisao nao encontrada"));

        if (revisao.isFinished()) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        if (unidadeRepository.findById(dto.getUnidadeId()).isEmpty()) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Unidade", "Unidade nao encontrada");
        }

        if (repository.findByRevisaoIdAndUnidadeId(dto.getRevisaoId(), dto.getUnidadeId()).isPresent()) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Unidade",
                    "Ja existe planejamento de geracao para esta unidade nesta revisao");
        }
    }
}
