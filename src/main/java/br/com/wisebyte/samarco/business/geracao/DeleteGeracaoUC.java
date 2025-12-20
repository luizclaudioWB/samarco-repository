package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteGeracaoUC {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    GeracaoValidationBusiness validationBusiness;

    @Transactional
    public void execute(Long id) {
        validationBusiness.validateForDelete(id);
        geracaoRepository.deleteById(id);
    }
}
