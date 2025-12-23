package br.com.wisebyte.samarco.business.calendario;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import br.com.wisebyte.samarco.mapper.calendario.CalendarioMapper;
import br.com.wisebyte.samarco.model.calendario.Calendario;
import br.com.wisebyte.samarco.repository.calendario.CalendarioRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class CreateCalendarioUC {

    @Inject
    CalendarioRepository calendarioRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    CalendarioMapper mapper;

    @Transactional
    public CalendarioDTO execute(@Valid CalendarioDTO dto) {
        var revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("Calendario", "Revisao", "Revisao nao encontrada"));

        // Verifica se já existe calendário para esta revisão
        if (calendarioRepository.findByRevisaoId(dto.getRevisaoId()).isPresent()) {
            throw new ValidadeExceptionBusiness("Calendario", "Calendario", "Ja existe calendario para esta revisao");
        }

        var entity = Calendario.builder()
                .revisao(revisao)
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

        calendarioRepository.save(entity);
        return mapper.toDTO(entity);
    }
}
