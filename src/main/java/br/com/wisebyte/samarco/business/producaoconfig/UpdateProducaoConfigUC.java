package br.com.wisebyte.samarco.business.producaoconfig;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.producao.ProducaoConfigDTO;
import br.com.wisebyte.samarco.mapper.producao.ProducaoConfigMapper;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class UpdateProducaoConfigUC {

    @Inject
    ProducaoConfigRepository repository;

    @Inject
    AreaRepository areaRepository;

    @Inject
    ProducaoConfigMapper mapper;

    @Inject
    ProducaoConfigValidationBusiness validator;

    @Inject
    EntityManager entityManager;

    @Transactional
    public ProducaoConfigDTO update(@NotNull ProducaoConfigDTO dto) {
        validate(dto);

        // Usa findByIdComAreas para carregar a entidade com as áreas (JOIN FETCH)
        ProducaoConfig atual = repository.findByIdComAreas(dto.getId())
            .orElseThrow(() -> new ValidadeExceptionBusiness("ProducaoConfig", "Id", "Configuracao de producao nao encontrada"));

        applyNewValues(atual, dto);

        // Usa EntityManager.merge() ao invés de repository.save()
        // porque StatelessSession não suporta bem ManyToMany collections
        ProducaoConfig saved = entityManager.merge(atual);
        entityManager.flush();

        return mapper.toDTO(saved);
    }

    private void validate(ProducaoConfigDTO dto) {
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness("ProducaoConfig", "Id", "Id nao pode ser nulo para atualizacao");
        }

        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness("ProducaoConfig", "Id", "Configuracao de producao nao encontrada");
        }

        ProducaoConfig atual = repository.findById(dto.getId()).orElseThrow();
        if (atual.getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness("ProducaoConfig", "Revisao", "Revisao finalizada nao pode ser alterada");
        }

        if (!validator.todasAreasExistem(dto.getAreaIds())) {
            throw new ValidadeExceptionBusiness("ProducaoConfig", "Areas", "Uma ou mais areas nao foram encontradas");
        }

        if (!validator.todasAreasAtivas(dto.getAreaIds())) {
            throw new ValidadeExceptionBusiness("ProducaoConfig", "Areas", "Uma ou mais areas estao desativadas");
        }
    }

    private void applyNewValues(ProducaoConfig entity, ProducaoConfigDTO dto) {
        entity.setMultiplicador(dto.getMultiplicador());

        Set<Area> novasAreas = new HashSet<>();
        if (dto.getAreaIds() != null) {
            for (Long areaId : dto.getAreaIds()) {
                areaRepository.findById(areaId).ifPresent(novasAreas::add);
            }
        }
        entity.setAreas(novasAreas);
    }
}
