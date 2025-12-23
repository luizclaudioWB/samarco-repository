package br.com.wisebyte.samarco.business.encargo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import br.com.wisebyte.samarco.mapper.encargo.EncargosSetoriaisMapper;
import br.com.wisebyte.samarco.repository.encargo.EncargosSetoriaisRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryEncargosSetoriaisUC {

    @Inject
    EncargosSetoriaisRepository encargosRepository;

    @Inject
    EncargosSetoriaisMapper mapper;

    public EncargosSetoriaisDTO findByRevisaoId(@NotNull Long revisaoId) {
        return encargosRepository.findByRevisaoId(revisaoId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ValidadeExceptionBusiness("Encargos", "Encargos", "Encargos nao encontrados para esta revisao"));
    }
}
