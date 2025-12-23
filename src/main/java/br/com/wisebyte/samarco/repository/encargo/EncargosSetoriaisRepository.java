package br.com.wisebyte.samarco.repository.encargo;

import br.com.wisebyte.samarco.model.encargo.EncargosSetoriais;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface EncargosSetoriaisRepository extends CrudRepository<EncargosSetoriais, Long> {

    @Query("SELECT e FROM EncargosSetoriais e WHERE e.revisao.id = ?1")
    Optional<EncargosSetoriais> findByRevisaoId(Long revisaoId);
}
