package br.com.wisebyte.samarco.repository.fornecedor;

import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

@Repository
public interface FornecedorRepository extends BasicRepository<Fornecedor, Long> {
}
