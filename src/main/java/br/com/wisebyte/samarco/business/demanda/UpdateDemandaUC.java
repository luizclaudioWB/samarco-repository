package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class UpdateDemandaUC {

    @Inject
    DemandaRepository repository;

    @Inject
    DemandaMapper mapper;

    @Transactional
    public DemandaDTO update(@NotNull DemandaDTO dto) {
        if (dto.getId() == null) {
            throw new ValidadeExceptionBusiness("Demanda", "Id", "Id nao pode ser nulo para atualizacao");
        }

        Demanda atual = repository.findById(dto.getId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("Demanda", "Id", "Demanda nao encontrada"));

        if (atual.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("Demanda", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        // Atualiza apenas os valores mensais (nao muda revisao/unidade/horario)
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
