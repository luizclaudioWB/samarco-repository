package br.com.wisebyte.samarco.mapper.tarifa;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class TarifaPlanejamentoMapper implements EntityMapper<TarifaPlanejamento, TarifaPlanejamentoDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Override
    public TarifaPlanejamento toEntity(TarifaPlanejamentoDTO dto) {
        return TarifaPlanejamento.builder()
                .id(dto.getId())
                .revisao(
                        dto.getRevisaoId() != null
                                ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
                                : null
                )
                .build();
    }

    @Override
    public TarifaPlanejamentoDTO toDTO(TarifaPlanejamento entity) {
        return TarifaPlanejamentoDTO.builder()
                .id(entity.getId())
                .revisaoId(
                        entity.getRevisao() != null
                                ? entity.getRevisao().getId()
                                : null
                )
                .build();
    }
}
