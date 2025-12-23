package br.com.wisebyte.samarco.mapper.encargo;

import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import br.com.wisebyte.samarco.model.encargo.EncargosSetoriais;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface EncargosSetoriaisMapper {

    @Mapping(source = "revisao.id", target = "revisaoId")
    EncargosSetoriaisDTO toDTO(EncargosSetoriais entity);
}
