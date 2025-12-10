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
public class UpdateAliquotaImpostosUC {

    @Inject
    AliquotaImpostosRepository repository;

    @Inject
    AliquotaImpostosMapper mapper;

    @Inject
    AliquotaImpostosValidationBusiness validator;

    @Transactional
    public AliquotaImpostosDTO update(@NotNull AliquotaImpostosDTO dto) {

        // Validacao 1: ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Id",
                    "ID e obrigatorio para atualizacao"
            );
        }

        // Validacao 2: Aliquota deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Id",
                    "Aliquota nao encontrada"
            );
        }

        // Validacao 3: Revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Revisao",
                    "Revisao finalizada nao pode ser alterada"
            );
        }

        // Validacao 4: Estado deve ser informado
        if (!validator.estadoInformado(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Estado",
                    "Estado e obrigatorio"
            );
        }

        // Validacao 5: Ano deve ser valido
        if (!validator.anoValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Ano",
                    "Ano deve estar entre 2020 e 2100"
            );
        }

        // Validacao 6: Combinacao deve continuar unica
        if (!validator.combinacaoUnica(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Combinacao",
                    "Ja existe aliquota cadastrada para este planejamento, ano e estado"
            );
        }

        // Validacao 7: Percentuais devem estar entre 0 e 100
        if (!validator.percentuaisValidos(dto)) {
            throw new ValidadeExceptionBusiness(
                    "AliquotaImpostos",
                    "Percentuais",
                    "Percentuais devem estar entre 0 e 100"
            );
        }

        // Busca entidade existente e aplica novos valores
        AliquotaImpostos existing = repository.findById(dto.getId()).orElseThrow();
        applyNewValues(existing, dto);

        AliquotaImpostos saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    private void applyNewValues(AliquotaImpostos entity, AliquotaImpostosDTO dto) {
        entity.setAno(dto.getAno());
        entity.setEstado(dto.getEstado());
        entity.setPercentualPis(dto.getPercentualPis());
        entity.setPercentualCofins(dto.getPercentualCofins());
        entity.setPercentualIcms(dto.getPercentualIcms());
        entity.setPercentualIpca(dto.getPercentualIpca());
    }
}
