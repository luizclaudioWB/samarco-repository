package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class DeletePlanejamentoGeracaoUC {

    @Inject
    PlanejamentoGeracaoRepository repository;

    @Transactional
    public void delete(@NotNull PlanejamentoGeracaoDTO dto) {
        if (dto.getId() == null) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Id nao pode ser nulo para exclusao");
        }

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Planejamento de geracao nao encontrado"));

        if (entity.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        repository.delete(entity);
    }
}
