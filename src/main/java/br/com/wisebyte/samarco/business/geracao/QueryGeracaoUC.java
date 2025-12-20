package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.GeracaoMapper;
import br.com.wisebyte.samarco.model.geracao.Geracao;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class QueryGeracaoUC {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    GeracaoMapper geracaoMapper;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    public QueryList<GeracaoDTO> list(Integer page, Integer size) {
        List<GeracaoDTO> items = geracaoRepository.findAll()
            .skip((long) page * size)
            .limit(size)
            .map(geracaoMapper::toDTO)
            .toList();

        long total = geracaoRepository.findAll().count();

        return new QueryList<>(items, total);
    }

    public GeracaoDTO findById(Long id) {
        Geracao entity = geracaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                "Geração não encontrada com o ID: " + id
            ));

        return geracaoMapper.toDTO(entity);
    }

    public List<GeracaoDTO> findByRevisao(Long revisaoId) {
        Revisao revisao = revisaoRepository.findById(revisaoId)
            .orElseThrow(() -> new NotFoundException(
                "Revisão não encontrada com o ID: " + revisaoId
            ));

        return geracaoRepository.findByRevisao(revisao)
            .stream()
            .map(geracaoMapper::toDTO)
            .toList();
    }

    public List<GeracaoDTO> findByUnidade(Long unidadeId) {
        Unidade unidade = unidadeRepository.findById(unidadeId)
            .orElseThrow(() -> new NotFoundException(
                "Unidade não encontrada com o ID: " + unidadeId
            ));

        return geracaoRepository.findByUnidade(unidade)
            .stream()
            .map(geracaoMapper::toDTO)
            .toList();
    }
}
