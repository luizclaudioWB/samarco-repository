package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaPlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaPlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class CreateTarifaPlanejamentoUC {

    @Inject
    TarifaPlanejamentoRepository repository;

    @Inject
    TarifaPlanejamentoMapper mapper;

    @Inject
    TarifaPlanejamentoValidationBusiness validator;

    @Transactional
    public TarifaPlanejamentoDTO create(@NotNull TarifaPlanejamentoDTO dto) {



        // Validacao 1: Revisao deve existir
        if (!validator.revisaoExists(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Revisao nao encontrada"
            );
        }

        // Validacao 2: Revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getRevisaoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas alteradas"
            );
        }

        // Validacao 3: Revisao nao pode ja ter TarifaPlanejamento
        if (!validator.revisaoUnica(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaPlanejamento",
                    "Revisao",
                    "Esta revisao ja possui uma Tarifa de Planejamento cadastrada"
            );
        }

        // Conversao e persistencia
        TarifaPlanejamento entity = mapper.toEntity(dto);
        TarifaPlanejamento saved = repository.save(entity);
        return mapper.toDTO(saved);
    }
}
