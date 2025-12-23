package br.com.wisebyte.samarco.mapper.calendario;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import br.com.wisebyte.samarco.model.calendario.Calendario;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CalendarioMapper implements EntityMapper<Calendario, CalendarioDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Override
    public Calendario toEntity(CalendarioDTO dto) {
        return Calendario.builder()
                .id(dto.getId())
                .revisao(dto.getRevisaoId() != null
                        ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null) : null)
                .qtdeHorasPontaDia(dto.getQtdeHorasPontaDia())
                .diasNaoUteisJaneiro(dto.getDiasNaoUteisJaneiro())
                .diasNaoUteisFevereiro(dto.getDiasNaoUteisFevereiro())
                .diasNaoUteisMarco(dto.getDiasNaoUteisMarco())
                .diasNaoUteisAbril(dto.getDiasNaoUteisAbril())
                .diasNaoUteisMaio(dto.getDiasNaoUteisMaio())
                .diasNaoUteisJunho(dto.getDiasNaoUteisJunho())
                .diasNaoUteisJulho(dto.getDiasNaoUteisJulho())
                .diasNaoUteisAgosto(dto.getDiasNaoUteisAgosto())
                .diasNaoUteisSetembro(dto.getDiasNaoUteisSetembro())
                .diasNaoUteisOutubro(dto.getDiasNaoUteisOutubro())
                .diasNaoUteisNovembro(dto.getDiasNaoUteisNovembro())
                .diasNaoUteisDezembro(dto.getDiasNaoUteisDezembro())
                .build();
    }

    @Override
    public CalendarioDTO toDTO(Calendario entity) {
        return CalendarioDTO.builder()
                .id(entity.getId())
                .revisaoId(entity.getRevisao() != null ? entity.getRevisao().getId() : null)
                .qtdeHorasPontaDia(entity.getQtdeHorasPontaDia())
                .diasNaoUteisJaneiro(entity.getDiasNaoUteisJaneiro())
                .diasNaoUteisFevereiro(entity.getDiasNaoUteisFevereiro())
                .diasNaoUteisMarco(entity.getDiasNaoUteisMarco())
                .diasNaoUteisAbril(entity.getDiasNaoUteisAbril())
                .diasNaoUteisMaio(entity.getDiasNaoUteisMaio())
                .diasNaoUteisJunho(entity.getDiasNaoUteisJunho())
                .diasNaoUteisJulho(entity.getDiasNaoUteisJulho())
                .diasNaoUteisAgosto(entity.getDiasNaoUteisAgosto())
                .diasNaoUteisSetembro(entity.getDiasNaoUteisSetembro())
                .diasNaoUteisOutubro(entity.getDiasNaoUteisOutubro())
                .diasNaoUteisNovembro(entity.getDiasNaoUteisNovembro())
                .diasNaoUteisDezembro(entity.getDiasNaoUteisDezembro())
                .build();
    }
}
