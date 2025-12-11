package br.com.wisebyte.samarco.business.tarifa.fornecedor;

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
    public TarifaFornecedorDTO create( @NotNull TarifaFornecedorDTO dto ) {
        validate( dto );
        TarifaFornecedor saved = repository.save( mapper.toEntity( dto ) );
        return mapper.toDTO( saved );
    }

    void validate( TarifaFornecedorDTO dto ) {
        if ( !validator.camposObrigatoriosPreenchidos( dto ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "Campos", "Todos os campos obrigatorios devem ser preenchidos" );
        }
        if ( !validator.tarifaPlanejamentoExists( dto.getTarifaPlanejamentoId( ) ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "TarifaPlanejamento", "Tarifa de Planejamento nao encontrada" );
        }
        if ( !validator.fornecedorExists( dto.getFornecedorId( ) ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "Fornecedor", "Fornecedor nao encontrado" );
        }
        if ( !validator.revisaoNaoFinalizada( dto.getTarifaPlanejamentoId( ) ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "Revisao", "Revisao finalizada nao pode ter tarifas alteradas" );
        }
        if ( !validator.ipcaValido( dto ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "IPCA", "Valores de IPCA nao podem ser negativos" );
        }
        if ( !validator.montanteValido( dto ) ) {
            throw new ValidadeExceptionBusiness( "TarifaFornecedor", "Montante", "Montante deve ser maior que zero" );
        }
    }
}
