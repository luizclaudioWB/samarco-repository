package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.mapper.producao.ProducaoConfigMapper;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.model.producao._ProducaoConfig;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryProducaoConfigUC {

    @Inject
    ProducaoConfigRepository repository;

    @Inject
    ProducaoConfigMapper mapper;

    public QueryList<ProducaoConfigDTO> list(@NotNull Integer page, @NotNull Integer size) {
        Page<ProducaoConfig> all = repository.findAll(
            PageRequest.ofPage(page, size, true),
            Order.by(_ProducaoConfig.id.asc())
        );
        return QueryList.<ProducaoConfigDTO>builder()
                .totalElements(all.totalElements())
                .totalPages(all.totalPages())
                .results(all.content().stream().map(mapper::toDTO).toList())
                .build();
    }

    public ProducaoConfigDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElse(null);
    }

    public ProducaoConfigDTO findByRevisaoId(Long revisaoId) {
        ProducaoConfig config = repository.findByRevisao_id(revisaoId);
        return config != null ? mapper.toDTO(config) : null;
    }
}
