package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.PlanejamentoConsumoEspecificoDTO;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeleteConsumoEspecificoUC {

    @Inject
    PlanejamentoConsumoEspecificoRepository repository;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public void delete(@NotNull PlanejamentoConsumoEspecificoDTO dto) {
        validate(dto);

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Consumo especifico nao encontrado"));

        repository.delete(entity);
    }

    private void validate(PlanejamentoConsumoEspecificoDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Id nao pode ser nulo para exclusao");
        }

        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Id", "Consumo especifico nao encontrado");
        }

        var entity = repository.findById(dto.getId()).orElseThrow();
        if (entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("ConsumoEspecifico", "Revisao", "Revisao finalizada nao pode ser alterada");
        }
    }
}
