package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeleteGeracaoUC {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    GeracaoValidationBusiness validator;

    @Transactional
    public void delete(@NotNull GeracaoDTO dto) {

        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Id", "Id da Geração é obrigatório para exclusão"
            );
        }

        if (!validator.geracaoExists(dto)) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Id", "Geração não encontrada"
            );
        }

        if (validator.isRevisaoFinished(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                "Geracao", "Revisão", "Esta revisão já foi finalizada e não pode ser alterada"
            );
        }

        geracaoRepository.deleteById(dto.getId());
    }
}
