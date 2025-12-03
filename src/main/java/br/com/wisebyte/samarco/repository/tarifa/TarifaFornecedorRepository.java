package br.com.wisebyte.samarco.repository.tarifa;

import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface TarifaFornecedorRepository extends BasicRepository<TarifaFornecedor, Long> {

    @Find
    List<TarifaFornecedor> findByPlanejamento(TarifaPlanejamento planejamento);

    @Find
    List<TarifaFornecedor> findByFornecedor(Fornecedor fornecedor);

    @Find
    List<TarifaFornecedor> findByPlanejamentoAndFornecedor(
            TarifaPlanejamento planejamento,
            Fornecedor fornecedor
    );
}
