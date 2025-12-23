package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.PlanejamentoGeracaoMapper;
import br.com.wisebyte.samarco.model.geracao.PlanejamentoGeracao;
import br.com.wisebyte.samarco.repository.geracao.PlanejamentoGeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class UpdatePlanejamentoGeracaoUC {

    @Inject
    PlanejamentoGeracaoRepository repository;

    @Inject
    PlanejamentoGeracaoMapper mapper;

    @Transactional
    public PlanejamentoGeracaoDTO update(@NotNull PlanejamentoGeracaoDTO dto) {
        if (dto.getId() == null) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Id nao pode ser nulo para atualizacao");
        }

        PlanejamentoGeracao atual = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("PlanejamentoGeracao", "Id", "Planejamento de geracao nao encontrado"));

        if (atual.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("PlanejamentoGeracao", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        atual.setValorJaneiro(dto.getValorJaneiro());
        atual.setValorFevereiro(dto.getValorFevereiro());
        atual.setValorMarco(dto.getValorMarco());
        atual.setValorAbril(dto.getValorAbril());
        atual.setValorMaio(dto.getValorMaio());
        atual.setValorJunho(dto.getValorJunho());
        atual.setValorJulho(dto.getValorJulho());
        atual.setValorAgosto(dto.getValorAgosto());
        atual.setValorSetembro(dto.getValorSetembro());
        atual.setValorOutubro(dto.getValorOutubro());
        atual.setValorNovembro(dto.getValorNovembro());
        atual.setValorDezembro(dto.getValorDezembro());

        var saved = repository.save(atual);
        return mapper.toDTO(saved);
    }
}
