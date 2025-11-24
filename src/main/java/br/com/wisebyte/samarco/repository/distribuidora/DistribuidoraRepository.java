package br.com.wisebyte.samarco.repository.distribuidora;

import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

@Repository
public interface DistribuidoraRepository extends BasicRepository<Distribuidora, Long> {
}
