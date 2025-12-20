package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.ConsumoEspecificoMapper;
import br.com.wisebyte.samarco.model.consumo.ConsumoEspecifico;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
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
public class QueryConsumoEspecificoUC {

    @Inject
    ConsumoEspecificoRepository consumoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    ConsumoEspecificoMapper consumoMapper;

    public QueryList<ConsumoEspecificoDTO> list(@NotNull Integer page, @NotNull Integer size) {
        Page<ConsumoEspecifico> resultado = consumoRepository.findAll(
            PageRequest.ofPage(page, size, true),
            Order.by(Sort.asc("id"))
        );

        return QueryList.<ConsumoEspecificoDTO>builder()
            .totalElements(resultado.totalElements())
            .totalPages(resultado.totalPages())
            .results(
                resultado.content().stream()
                    .map(consumoMapper::toDTO)
                    .toList()
            )
            .build();
    }

    public ConsumoEspecificoDTO findById(Long id) {
        return consumoRepository.findById(id)
            .map(consumoMapper::toDTO)
            .orElse(null);
    }

    public List<ConsumoEspecificoDTO> findByRevisao(Long revisaoId) {
        var revisao = revisaoRepository.findById(revisaoId).orElse(null);
        if (revisao == null) {
            return List.of();
        }

        return consumoRepository.findByRevisao(revisao)
            .stream()
            .map(consumoMapper::toDTO)
            .toList();
    }
}
