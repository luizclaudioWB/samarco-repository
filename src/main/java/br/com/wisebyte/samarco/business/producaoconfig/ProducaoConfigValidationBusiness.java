package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Set;

@ApplicationScoped
public class ProducaoConfigValidationBusiness {

    @Inject
    ProducaoConfigRepository producaoConfigRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    public boolean idIsNull(ProducaoConfigDTO dto) {
        return dto.getId() == null;
    }

    public boolean exists(Long id) {
        return id != null && producaoConfigRepository.findById(id).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        return revisaoId != null && revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean revisaoNaoFinalizada(Long revisaoId) {
        return revisaoRepository.findById(revisaoId)
                .map(revisao -> !revisao.isFinished())
                .orElse(false);
    }

    public boolean revisaoUnica(ProducaoConfigDTO dto) {
        ProducaoConfig existente = producaoConfigRepository.findByRevisao_id(dto.getRevisaoId());

        if (existente == null) return true;

        return existente.getId().equals(dto.getId());
    }

    public boolean todasAreasExistem(Set<Long> areaIds) {
        if (areaIds == null || areaIds.isEmpty()) {
            return true;
        }

        for (Long areaId : areaIds) {
            if (areaRepository.findById(areaId).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean todasAreasAtivas(Set<Long> areaIds) {
        if (areaIds == null || areaIds.isEmpty()) {
            return true;
        }

        for (Long areaId : areaIds) {
            boolean ativa = areaRepository.findById(areaId)
                    .map(Area::isAtivo)
                    .orElse(false);
            if (!ativa) {
                return false;
            }
        }
        return true;
    }

    public boolean areaEstaNaProducaoConfig(Long revisaoId, Long areaId) {
        ProducaoConfig config = producaoConfigRepository.findByRevisao_id(revisaoId);
        if (config == null || config.getAreas() == null || config.getAreas().isEmpty()) {
            return false;
        }

        return config.getAreas().stream()
                .anyMatch(area -> area.getId().equals(areaId));
    }
}
