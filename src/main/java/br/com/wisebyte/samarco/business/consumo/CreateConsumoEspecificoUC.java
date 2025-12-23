package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.PlanejamentoConsumoEspecificoMapper;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateConsumoEspecificoUC {

    @Inject
    PlanejamentoConsumoEspecificoRepository repository;

    @Inject
    PlanejamentoConsumoEspecificoMapper mapper;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public PlanejamentoConsumoEspecificoDTO create(@NotNull PlanejamentoConsumoEspecificoDTO dto) {
        validate(dto);
        var entity = mapper.toEntity(dto);
        var saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    private void validate(PlanejamentoConsumoEspecificoDTO dto) {
        if (!validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Id deve ser nulo para criacao");
        }

        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Revisao", "Revisao nao encontrada");
        }

        if (!validator.revisaoNaoFinalizada(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        if (!validator.areaExists(dto.getAreaId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Area", "Area nao encontrada");
        }

        if (!validator.combinacaoUnica(dto)) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Area", "Ja existe consumo especifico cadastrado para esta area nesta revisao");
        }
    }
}
