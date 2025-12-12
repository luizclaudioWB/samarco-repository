package br.com.wisebyte.samarco.mapper.producao;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProducaoConfigMapper implements EntityMapper<ProducaoConfig, ProducaoConfigDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Override
    public ProducaoConfig toEntity(ProducaoConfigDTO dto) {
        Set<Area> areas = new HashSet<>();
        if (dto.getAreaIds() != null && !dto.getAreaIds().isEmpty()) {
            for (Long areaId : dto.getAreaIds()) {
                areaRepository.findById(areaId).ifPresent(areas::add);
            }
        }

        return ProducaoConfig.builder()
                .id(dto.getId())
                .revisao(
                    dto.getRevisaoId() != null
                    ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
                    : null
                )
                .multiplicador(dto.getMultiplicador())
                .areas(areas)
                .build();
    }

    @Override
    public ProducaoConfigDTO toDTO(ProducaoConfig entity) {
        Set<Long> areaIds = entity.getAreas() != null
                ? entity.getAreas().stream().map(Area::getId).collect(Collectors.toSet())
                : new HashSet<>();

        return ProducaoConfigDTO.builder()
                .id(entity.getId())
                .revisaoId(entity.getRevisao() != null ? entity.getRevisao().getId() : null)
                .multiplicador(entity.getMultiplicador())
                .areaIds(areaIds)
                .build();
    }
}
