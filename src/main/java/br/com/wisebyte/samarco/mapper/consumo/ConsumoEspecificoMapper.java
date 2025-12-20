package br.com.wisebyte.samarco.mapper.consumo;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.model.consumo.ConsumoEspecifico;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConsumoEspecificoMapper implements EntityMapper<ConsumoEspecifico, ConsumoEspecificoDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Override
    public ConsumoEspecifico toEntity(ConsumoEspecificoDTO dto) {
        return ConsumoEspecifico.builder()
            .id(dto.getId())
            .revisao(
                dto.getRevisaoId() != null
                    ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null)
                    : null
            )
            .area(
                dto.getAreaId() != null
                    ? areaRepository.findById(dto.getAreaId()).orElse(null)
                    : null
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
    public ConsumoEspecificoDTO toDTO(ConsumoEspecifico entity) {
        return ConsumoEspecificoDTO.builder()
            .id(entity.getId())
            .revisaoId(
                entity.getRevisao() != null
                    ? entity.getRevisao().getId()
                    : null
            )
            .areaId(
                entity.getArea() != null
                    ? entity.getArea().getId()
                    : null
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
