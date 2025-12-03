package br.com.wisebyte.samarco.mapper.tarifa;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AliquotaImpostosMapper implements EntityMapper<AliquotaImpostos, AliquotaImpostosDTO> {

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;


    @Override
    public AliquotaImpostos toEntity(AliquotaImpostosDTO dto) {
        return AliquotaImpostos.builder()
                .id(dto.getId())
                .planejamento(
                        dto.getTarifaPlanejamentoId() != null
                                ? tarifaPlanejamentoRepository
                                    .findById(dto.getTarifaPlanejamentoId())
                                    .orElse(null)
                                : null
                )
                .ano(dto.getAno())
                .estado(dto.getEstado())
                .percentualPis(dto.getPercentualPis())
                .percentualCofins(dto.getPercentualCofins())
                .percentualIcms(dto.getPercentualIcms())
                .percentualIpca(dto.getPercentualIpca())
                .build();
    }


    @Override
    public AliquotaImpostosDTO toDTO(AliquotaImpostos entity) {
        return AliquotaImpostosDTO.builder()
                .id(entity.getId())
                .tarifaPlanejamentoId(
                        entity.getPlanejamento() != null
                                ? entity.getPlanejamento().getId()
                                : null
                )
                .ano(entity.getAno())
                .estado(entity.getEstado())
                .percentualPis(entity.getPercentualPis())
                .percentualCofins(entity.getPercentualCofins())
                .percentualIcms(entity.getPercentualIcms())
                .percentualIpca(entity.getPercentualIpca())
                .build();
    }
}
