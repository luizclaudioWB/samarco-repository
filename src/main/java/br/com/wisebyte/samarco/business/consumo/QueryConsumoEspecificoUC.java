package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.PlanejamentoConsumoEspecificoMapper;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QueryConsumoEspecificoUC {

    @Inject
    PlanejamentoConsumoEspecificoRepository repository;

    @Inject
    PlanejamentoConsumoEspecificoMapper mapper;

    public PlanejamentoConsumoEspecificoDTO findById(@NotNull Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Consumo especifico nao encontrado"));
    }

    public QueryList<PlanejamentoConsumoEspecificoDTO> findByRevisaoId(@NotNull Long revisaoId) {
        List<PlanejamentoConsumoEspecificoDTO> list = repository.findByRevisaoId(revisaoId).stream()
                .map(mapper::toDTO)
                .toList();

        return QueryList.<PlanejamentoConsumoEspecificoDTO>builder()
                .totalElements((long) list.size())
                .totalPages(1L)
                .results(list)
                .build();
    }

    public PlanejamentoConsumoEspecificoDTO findByRevisaoIdAndAreaId(@NotNull Long revisaoId, @NotNull Long areaId) {
        return repository.findByRevisaoIdAndAreaId(revisaoId, areaId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("ConsumoEspecifico", "Area", "Consumo especifico nao encontrado para esta area"));
    }
}
