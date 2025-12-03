package br.com.wisebyte.samarco.mapper.planejamento;


import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanejamentoProducaoMapper implements EntityMapper<PlanejamentoProducao, PlanejamentoProducaoDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Override
    public PlanejamentoProducao toEntity(PlanejamentoProducaoDTO dto){
        return PlanejamentoProducao.builder()
                .id(dto.getId())
                .revisao(
                        dto.getRevisaoId() != null
                        ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null) : null
                )
                .area(
                        dto.getAreaId() != null
                        ? areaRepository.findById(dto.getAreaId()).orElse(null) : null
                )

                .valorJaneiro(dto.getValorJaneiro())
                .valorFevereiro(dto.getValorFevereiro())
                .valorMarco(dto.getValorMarco())
                .valorAbril(dto.getValorAbril())
                .valorMaio(dto.getValorMaio())
                .valorJunho(dto.getValorJunho())
                .valorJulho(dto.getValorJulho())
                .valorAgosto(dto.getValorAgosto())
                .valorSetembro(dto.getValorSetembro())
                .valorOutubro(dto.getValorOutubro())
                .valorNovembro(dto.getValorNovembro())
                .valorDezembro(dto.getValorDezembro())
                .build();
    }

    @Override
    public PlanejamentoProducaoDTO toDTO(PlanejamentoProducao entity) {
        return PlanejamentoProducaoDTO.builder()
                .id(entity.getId())
                .revisaoId(
                        entity.getRevisao() != null
                        ? entity.getRevisao().getId() : null
                )
                .areaId(
                        entity.getArea() != null
                        ? entity.getArea().getId() : null
                )
                .valorJaneiro(entity.getValorJaneiro())
                .valorFevereiro(entity.getValorFevereiro())
                .valorMarco(entity.getValorMarco())
                .valorAbril(entity.getValorAbril())
                .valorMaio(entity.getValorMaio())
                .valorJunho(entity.getValorJunho())
                .valorJulho(entity.getValorJulho())
                .valorAgosto(entity.getValorAgosto())
                .valorSetembro(entity.getValorSetembro())
                .valorOutubro(entity.getValorOutubro())
                .valorNovembro(entity.getValorNovembro())
                .valorDezembro(entity.getValorDezembro())
                .build();
    }
}