package br.com.wisebyte.samarco.mapper.planejamento;

import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class PlanejamentoMapper {

    public Planejamento toEntity(PlanejamentoDTO dto) {
        return Planejamento.builder()
                .id(dto.getId())
                .ano(dto.getAno())
                .descricao(dto.getDescricao())
                .mensagem(dto.getMensagem())
                .corrente(dto.getCorrente())
                .build();
    }

    public PlanejamentoDTO toDTO(Planejamento entity) {
        return PlanejamentoDTO.builder()
                .id(entity.getId())
                .ano(entity.getAno())
                .descricao(entity.getDescricao())
                .mensagem(entity.getMensagem())
                .corrente(entity.getCorrente())
                .build();
    }
}
