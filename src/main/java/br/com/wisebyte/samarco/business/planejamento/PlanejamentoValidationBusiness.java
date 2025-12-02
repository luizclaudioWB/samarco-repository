package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class PlanejamentoValidationBusiness {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    public boolean idIsNull(PlanejamentoDTO dto) {
        return dto.getId() == null;
    }

    public boolean planningExists(PlanejamentoDTO dto) {
        if (dto.getId() == null) {
            return false;
        }
        return planejamentoRepository.findById(dto.getId()).isPresent();
    }

    public boolean planningExistsForYear(PlanejamentoDTO dto) {
        if (dto.getAno() == null) {
            return false;
        }
        return planejamentoRepository.findByAno(dto.getAno()).isPresent();
    }


    public boolean anotherPlanningExistsForYear(PlanejamentoDTO dto) {
        if (dto.getAno() == null || dto.getId() == null) {
            return false;
        }

        Optional<Planejamento> planejamento = planejamentoRepository.findByAno(dto.getAno());

        // Se não existe nenhum planejamento para o ano, retorna false
        if (planejamento.isEmpty()) {
            return false;
        }

        // Se existe, verifica se é diferente do planejamento atual
        // Retorna true se for um OUTRO planejamento (IDs diferentes)
        return !planejamento.get().getId().equals(dto.getId());
    }

    public boolean currentPlanningExists() {
        return planejamentoRepository.findByCorrente(true).isPresent();
    }


    public boolean anotherCurrentPlanningExists(PlanejamentoDTO dto) {
        if (dto.getId() == null) {
            return false;
        }

        Optional<Planejamento> planejamentoCorrente = planejamentoRepository.findByCorrente(true);

        // Se não existe nenhum planejamento corrente, retorna false
        if (planejamentoCorrente.isEmpty()) {
            return false;
        }

        // Se existe, verifica se é diferente do planejamento atual
        // Retorna true se for um OUTRO planejamento (IDs diferentes)
        return !planejamentoCorrente.get().getId().equals(dto.getId());
    }

    public boolean isDescriptionValid(PlanejamentoDTO dto) {
        return dto.getDescricao() != null && !dto.getDescricao().trim().isEmpty();
    }


    public boolean isYearValid(PlanejamentoDTO dto) {
        if (dto.getAno() == null) {
            return false;
        }
        // razoável para planejamento energético
        return dto.getAno() >= 2000 && dto.getAno() <= 2100;
    }
}
