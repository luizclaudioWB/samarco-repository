package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QueryDemandaUC {

    @Inject
    DemandaRepository repository;

    @Inject
    DemandaMapper mapper;

    public DemandaDTO findById(@NotNull Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("Demanda", "Id", "Demanda nao encontrada"));
    }

    public QueryList<DemandaDTO> findByRevisaoId(@NotNull Long revisaoId) {
        List<DemandaDTO> list = repository.findByRevisaoId(revisaoId).stream()
                .map(mapper::toDTO)
                .toList();

        return QueryList.<DemandaDTO>builder()
                .totalElements((long) list.size())
                .totalPages(1L)
                .results(list)
                .build();
    }
}
