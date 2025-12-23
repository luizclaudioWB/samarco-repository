package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.PlanejamentoGeracaoMapper;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QueryPlanejamentoGeracaoUC {

    @Inject
    PlanejamentoGeracaoRepository repository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    PlanejamentoGeracaoMapper mapper;

    public PlanejamentoGeracaoDTO findById(@NotNull Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Planejamento de geracao nao encontrado"));
    }

    public QueryList<PlanejamentoGeracaoDTO> findByRevisaoId(@NotNull Long revisaoId) {
        List<PlanejamentoGeracaoDTO> list = repository.findByRevisaoId(revisaoId).stream()
                .map(mapper::toDTO)
                .toList();

        return QueryList.<PlanejamentoGeracaoDTO>builder()
                .totalElements((long) list.size())
                .totalPages(1L)
                .results(list)
                .build();
    }

    public List<PlanejamentoGeracaoDTO> findByUnidade(Long unidadeId) {
        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new ValidadeExceptionBusiness(
                        "PlanejamentoGeracao", "Unidade", "Unidade nao encontrada com o ID: " + unidadeId
                ));

        return repository.findByUnidade(unidade)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
