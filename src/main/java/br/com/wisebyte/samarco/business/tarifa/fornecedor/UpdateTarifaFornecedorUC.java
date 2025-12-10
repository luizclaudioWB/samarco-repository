package br.com.wisebyte.samarco.business.tarifa.fornecedor;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaFornecedorMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UpdateTarifaFornecedorUC {

    @Inject
    TarifaFornecedorRepository repository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TarifaFornecedorMapper mapper;

    @Inject
    TarifaFornecedorValidationBusiness validator;

    @Transactional
    public TarifaFornecedorDTO update(@NotNull TarifaFornecedorDTO dto) {

        // 1. ID deve ser informado
        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Id",
                    "ID e obrigatorio para atualizacao"
            );
        }

        // 2. Tarifa deve existir
        if (!validator.exists(dto.getId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Id",
                    "Tarifa de Fornecedor nao encontrada"
            );
        }

        // 3. Busca entidade existente
        TarifaFornecedor existing = repository.findById(dto.getId()).orElseThrow();

        // 4. Revisao atual nao pode estar finalizada
        if (existing.getPlanejamento() != null &&
                existing.getPlanejamento().getRevisao() != null &&
                existing.getPlanejamento().getRevisao().isFinished()) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Revisao",
                    "Revisao finalizada nao pode ter tarifas alteradas"
            );
        }

        // 5. IPCA valido
        if (!validator.ipcaValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "IPCA",
                    "Valores de IPCA nao podem ser negativos"
            );
        }

        // 6. Montante valido
        if (!validator.montanteValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Montante",
                    "Montante deve ser maior que zero"
            );
        }

        // 7. Aplica novos valores
        applyNewValues(existing, dto);

        TarifaFornecedor saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    private void applyNewValues(TarifaFornecedor entity, TarifaFornecedorDTO dto) {
        // Atualiza fornecedor se informado
        if (dto.getFornecedorId() != null) {
            entity.setFornecedor(
                    fornecedorRepository.findById(dto.getFornecedorId()).orElse(null)
            );
        }

        entity.setIpcaRealizada(dto.getIpcaRealizada());
        entity.setIpcaProjetado(dto.getIpcaProjetado());
        entity.setMontante(dto.getMontante());
    }
}
