package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaDistribuidoraMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class CreateTarifaDistribuidoraUC {

    @Inject
    TarifaDistribuidoraRepository repository;

    @Inject
    TarifaDistribuidoraMapper mapper;

    @Inject
    TarifaDistribuidoraValidationBusiness validator;

    @Transactional
    public TarifaDistribuidoraDTO create(@NotNull TarifaDistribuidoraDTO dto) {

        // 1. TarifaPlanejamento deve existir
        if (!validator.tarifaPlanejamentoExists(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "TarifaPlanejamento",
                    "Tarifa de Planejamento nao encontrada"
            );
        }

        // 2. Distribuidora deve existir
        if (!validator.distribuidoraExists(dto.getDistribuidoraId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Distribuidora",
                    "Distribuidora nao encontrada"
            );
        }

        // 3. Revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas alteradas"
            );
        }

        // 4. Periodo deve ser valido
        if (!validator.periodoValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Periodo",
                    "Periodo inicial deve ser anterior ou igual ao periodo final"
            );
        }

        // 5. Valores devem ser positivos
        if (!validator.valoresPositivos(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Valores",
                    "Todos os valores monetarios devem ser positivos ou zero"
            );
        }

        // 6. ICMS consistente
        if (!validator.icmsConsistente(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "ICMS",
                    "Se sobrescrever ICMS, deve informar percentual valido (0-100)"
            );
        }

        // 7. Horas de ponta validas
        if (!validator.horasPontaValida(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaDistribuidora",
                    "Horas Ponta",
                    "Quantidade de horas de ponta deve ser entre 1 e 744"
            );
        }

        // Persistencia
        TarifaDistribuidora entity = mapper.toEntity(dto);
        TarifaDistribuidora saved = repository.save(entity);
        return mapper.toDTO(saved);
    }
}
