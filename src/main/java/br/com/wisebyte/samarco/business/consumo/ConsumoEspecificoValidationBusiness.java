package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class ConsumoEspecificoValidationBusiness {

    @Inject
    ConsumoEspecificoRepository consumoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    public boolean idIsNull(ConsumoEspecificoDTO dto) {
        return dto.getId() == null;
    }

    public boolean consumoExists(ConsumoEspecificoDTO dto) {
        if (dto.getId() == null) return false;
        return consumoRepository.findById(dto.getId()).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean areaExists(Long areaId) {
        if (areaId == null) return false;
        return areaRepository.findById(areaId).isPresent();
    }

    public boolean isDuplicateKey(ConsumoEspecificoDTO dto) {
        Optional<Revisao> revisaoOpt = revisaoRepository.findById(dto.getRevisaoId());
        Optional<Area> areaOpt = areaRepository.findById(dto.getAreaId());

        if (revisaoOpt.isEmpty() || areaOpt.isEmpty()) {
            return false;
        }

        var existing = consumoRepository.findByRevisaoAndArea(
            revisaoOpt.get(),
            areaOpt.get()
        );

        if (existing.isEmpty()) {
            return false;
        }

        return !existing.get().getId().equals(dto.getId());
    }

    public boolean isRevisaoFinished(Long revisaoId) {
        if (revisaoId == null) return false;
        return revisaoRepository.findById(revisaoId)
            .map(Revisao::isFinished)
            .orElse(false);
    }
}
