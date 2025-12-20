package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QueryDemandaUC {

    @Inject
    DemandaRepository demandaRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    DemandaMapper demandaMapper;

    public QueryList<DemandaDTO> list(@NotNull Integer page, @NotNull Integer size) {
        Page<Demanda> resultado = demandaRepository.findAll(
            PageRequest.ofPage(page, size, true),
            Order.by(Sort.asc("id"))
        );

        return QueryList.<DemandaDTO>builder()
            .totalElements(resultado.totalElements())
            .totalPages(resultado.totalPages())
            .results(
                resultado.content().stream()
                    .map(demandaMapper::toDTO)
                    .toList()
            )
            .build();
    }

    public DemandaDTO findById(Long id) {
        return demandaRepository.findById(id)
            .map(demandaMapper::toDTO)
            .orElse(null);
    }

    public List<DemandaDTO> findByRevisao(Long revisaoId) {
        var revisao = revisaoRepository.findById(revisaoId).orElse(null);
        if (revisao == null) {
            return List.of();
        }

        return demandaRepository.findByRevisao(revisao)
            .stream()
            .map(demandaMapper::toDTO)
            .toList();
    }
}
