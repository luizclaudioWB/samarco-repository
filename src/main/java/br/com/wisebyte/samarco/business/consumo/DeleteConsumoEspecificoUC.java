package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.consumo.ConsumoEspecificoDTO;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteConsumoEspecificoUC {

    @Inject
    ConsumoEspecificoRepository consumoRepository;

    @Inject
    ConsumoEspecificoValidationBusiness validator;

    @Transactional
    public void delete(ConsumoEspecificoDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Id", "Id não deve ser nulo"
            );
        }

        if (!validator.consumoExists(dto)) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Id", "Consumo específico não encontrado"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "ConsumoEspecifico", "Revisão",
                "Não é possível excluir de uma revisão finalizada"
            );
        }

        consumoRepository.deleteById(dto.getId());
    }
}
