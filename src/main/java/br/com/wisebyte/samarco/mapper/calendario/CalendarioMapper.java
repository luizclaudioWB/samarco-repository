package br.com.wisebyte.samarco.mapper.calendario;

import br.com.wisebyte.samarco.dto.calendario.CalendarioDTO;
import br.com.wisebyte.samarco.model.calendario.Calendario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface CalendarioMapper {

    @Mapping(source = "revisao.id", target = "revisaoId")
    CalendarioDTO toDTO(Calendario entity);
}
