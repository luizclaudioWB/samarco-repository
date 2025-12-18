package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.mapper.producao.ProducaoConfigMapper;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class QueryProducaoConfigUC {

    @Inject
    ProducaoConfigRepository repository;

    @Inject
    ProducaoConfigMapper mapper;

    public QueryList<ProducaoConfigDTO> list(@NotNull Integer page, @NotNull Integer size) {
        List<ProducaoConfig> all = repository.findAllComAreas();
        all.sort(Comparator.comparing(ProducaoConfig::getId));

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, all.size());

        List<ProducaoConfigDTO> pageContent = all
                .subList(Math.min(fromIndex, all.size()), toIndex)
                .stream()
                .map(mapper::toDTO)
                .toList();

        long totalPages = (long) Math.ceil((double) all.size() / size);

        return QueryList.<ProducaoConfigDTO>builder()
                .totalElements((long) all.size())
                .totalPages(totalPages)
                .results(pageContent)
                .build();
    }

    public ProducaoConfigDTO findById(Long id) {
        return repository.findByIdComAreas(id).map(mapper::toDTO).orElse(null);
    }

    public ProducaoConfigDTO findByRevisaoId(Long revisaoId) {
        return repository.findByRevisaoIdComAreas(revisaoId).map(mapper::toDTO).orElse(null);
    }
}
