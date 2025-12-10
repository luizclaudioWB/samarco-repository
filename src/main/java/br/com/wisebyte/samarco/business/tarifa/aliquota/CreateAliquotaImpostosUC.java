package br.com.wisebyte.samarco.business.tarifa.aliquota;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.AliquotaImpostosDTO;
import br.com.wisebyte.samarco.mapper.tarifa.AliquotaImpostosMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class CreateAliquotaImpostosUC {

    @Inject
    AliquotaImpostosRepository repository;

    @Inject
    AliquotaImpostosMapper mapper;

    @Inject
    AliquotaImpostosValidationBusiness validator;

    @Transactional
    public AliquotaImpostosDTO create(@NotNull AliquotaImpostosDTO dto) {

        // Validacao 1: TarifaPlanejamento deve existir
        if (!validator.tarifaPlanejamentoExists(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "TarifaPlanejamento",
                    "Tarifa de Planejamento nao encontrada"
            );
        }

        // Validacao 2: Revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Revisao",
                    "Revisao finalizada nao pode ser alterada"
            );
        }

        // Validacao 3: Estado deve ser informado
        if (!validator.estadoInformado(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Estado",
                    "Estado e obrigatorio"
            );
        }

        // Validacao 4: Ano deve ser valido
        if (!validator.anoValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Ano",
                    "Ano deve estar entre 2020 e 2100"
            );
        }

        // Validacao 5: Combinacao (planejamento + ano + estado) deve ser unica
        if (!validator.combinacaoUnica(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Combinacao",
                    "Ja existe aliquota cadastrada para este planejamento, ano e estado"
            );
        }

        // Validacao 6: Percentuais devem estar entre 0 e 100
        if (!validator.percentuaisValidos(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Percentuais",
                    "Percentuais devem estar entre 0 e 100"
            );
        }

        // Conversao e persistencia
        AliquotaImpostos entity = mapper.toEntity(dto);
        AliquotaImpostos saved = repository.save(entity);
        return mapper.toDTO(saved);
    }
}
