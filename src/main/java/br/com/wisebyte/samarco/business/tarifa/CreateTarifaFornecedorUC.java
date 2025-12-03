package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaFornecedorMapper;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class CreateTarifaFornecedorUC {

    @Inject
    TarifaFornecedorRepository repository;

    @Inject
    TarifaFornecedorMapper mapper;

    @Inject
    TarifaFornecedorValidationBusiness validator;

    @Transactional
    public TarifaFornecedorDTO create(@NotNull TarifaFornecedorDTO dto) {

        // 1. Campos obrigatorios
        if (!validator.camposObrigatoriosPreenchidos(dto)) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Campos",
                    "Todos os campos obrigatorios devem ser preenchidos"
            );
        }

        // 2. TarifaPlanejamento deve existir
        if (!validator.tarifaPlanejamentoExists(dto.getTarifaPlanejamentoId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "TarifaPlanejamento",
                    "Tarifa de Planejamento nao encontrada"
            );
        }

        // 3. Fornecedor deve existir
        if (!validator.fornecedorExists(dto.getFornecedorId())) {
            throw new ValidadeExceptionBusiness(
                    "TarifaFornecedor",
                    "Fornecedor",
                    "Fornecedor nao encontrado"
            );
        }

        // 4. Revisao nao pode estar finalizada
        if (!validator.revisaoNaoFinalizada(dto.getTarifaPlanejamentoId())) {
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

        // 7. Persistencia
        TarifaFornecedor entity = mapper.toEntity(dto);
        TarifaFornecedor saved = repository.save(entity);
        return mapper.toDTO(saved);
    }
}
