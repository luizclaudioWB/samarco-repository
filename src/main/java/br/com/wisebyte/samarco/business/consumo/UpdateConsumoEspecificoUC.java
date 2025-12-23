package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import br.com.wisebyte.samarco.mapper.consumo.PlanejamentoConsumoEspecificoMapper;
import br.com.wisebyte.samarco.model.consumo.PlanejamentoConsumoEspecifico;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class UpdateConsumoEspecificoUC {

    @Inject
    PlanejamentoConsumoEspecificoRepository repository;

    @Inject
    PlanejamentoConsumoEspecificoMapper mapper;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public PlanejamentoConsumoEspecificoDTO update(@NotNull PlanejamentoConsumoEspecificoDTO dto) {
        validate(dto);

        PlanejamentoConsumoEspecifico atual = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Consumo especifico nao encontrado"));

        applyNewValues(atual, dto);
        var saved = repository.save(atual);
        return mapper.toDTO(saved);
    }

    private void validate(PlanejamentoConsumoEspecificoDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Id nao pode ser nulo para atualizacao");
        }

        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Consumo especifico nao encontrado");
        }

        PlanejamentoConsumoEspecifico atual = repository.findById(dto.getId()).orElseThrow();
        if (atual.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        if (!validator.areaExists(dto.getAreaId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Area", "Area nao encontrada");
        }

        if (!validator.combinacaoUnica(dto)) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Area", "Ja existe consumo especifico cadastrado para esta area nesta revisao");
        }
    }

    private void applyNewValues(PlanejamentoConsumoEspecifico entity, PlanejamentoConsumoEspecificoDTO dto) {
        entity.setValorJaneiro(dto.getValorJaneiro());
        entity.setValorFevereiro(dto.getValorFevereiro());
        entity.setValorMarco(dto.getValorMarco());
        entity.setValorAbril(dto.getValorAbril());
        entity.setValorMaio(dto.getValorMaio());
        entity.setValorJunho(dto.getValorJunho());
        entity.setValorJulho(dto.getValorJulho());
        entity.setValorAgosto(dto.getValorAgosto());
        entity.setValorSetembro(dto.getValorSetembro());
        entity.setValorOutubro(dto.getValorOutubro());
        entity.setValorNovembro(dto.getValorNovembro());
        entity.setValorDezembro(dto.getValorDezembro());
    }
}
