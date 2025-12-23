package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConsumoEspecificoValidationBusiness {

    @Inject
    PlanejamentoConsumoEspecificoRepository repository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    public boolean idIsNull(PlanejamentoConsumoEspecificoDTO dto) {
        return dto.getId() == null;
    }

    public boolean exists(Long id) {
        return id != null && repository.findById(id).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        return revisaoId != null && revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean areaExists(Long areaId) {
        return areaId != null && areaRepository.findById(areaId).isPresent();
    }

    public boolean revisaoNaoFinalizada(Long revisaoId) {
        return revisaoRepository.findById(revisaoId)
                .map(revisao -> !revisao.isFinished())
                .orElse(false);
    }

    public boolean combinacaoUnica(PlanejamentoConsumoEspecificoDTO dto) {
        var existing = repository.findByRevisaoIdAndAreaId(dto.getRevisaoId(), dto.getAreaId());
        if (existing.isEmpty()) {
            return true;
        }
        // Se estamos editando, verifica se o registro encontrado e o mesmo
        return dto.getId() != null && existing.get().getId().equals(dto.getId());
    }
}
