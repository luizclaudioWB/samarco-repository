package br.com.wisebyte.samarco.business.demanda;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.demanda.DemandaDTO;
import br.com.wisebyte.samarco.mapper.demanda.DemandaMapper;
import br.com.wisebyte.samarco.repository.demanda.DemandaRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateDemandaUC {

    @Inject
    DemandaRepository repository;

    @Inject
    DemandaMapper mapper;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Transactional
    public DemandaDTO create(@NotNull DemandaDTO dto) {
        validate(dto);
        var entity = mapper.toEntity(dto);
        var saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    private void validate(DemandaDTO dto) {
        if (dto.getId() != null) {
            throw new ValidadeExceptionBusiness("Demanda", "Id", "Id deve ser nulo para criacao");
        }

        var revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("Demanda", "Revisao", "Revisao nao encontrada"));

        if (revisao.isFinished()) {
            throw new ValidadeExceptionBusiness("Demanda", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        if (unidadeRepository.findById(dto.getUnidadeId()).isEmpty()) {
            throw new ValidadeExceptionBusiness("Demanda", "Unidade", "Unidade nao encontrada");
        }

        // Verifica duplicidade (revisao + unidade + tipoHorario)
        var existing = repository.findByRevisaoIdAndUnidadeId(dto.getRevisaoId(), dto.getUnidadeId());
        boolean duplicado = existing.stream()
                .anyMatch(d -> d.getTipoHorario() == dto.getTipoHorario());
        if (duplicado) {
            throw new ValidadeExceptionBusiness("Demanda", "TipoHorario",
                    "Ja existe demanda para esta unidade e horario nesta revisao");
        }
    }
}
