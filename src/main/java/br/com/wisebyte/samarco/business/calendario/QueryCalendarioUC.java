package br.com.wisebyte.samarco.business.calendario;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import br.com.wisebyte.samarco.mapper.calendario.CalendarioMapper;
import br.com.wisebyte.samarco.repository.calendario.CalendarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryCalendarioUC {

    @Inject
    CalendarioRepository calendarioRepository;

    @Inject
    CalendarioMapper mapper;

    public CalendarioDTO findByRevisaoId(@NotNull Long revisaoId) {
        return calendarioRepository.findByRevisaoId(revisaoId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("Calendario", "Calendario", "Calendario nao encontrado para esta revisao"));
    }
}
