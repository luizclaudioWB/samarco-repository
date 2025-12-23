package br.com.wisebyte.samarco.repository.calendario;

import br.com.wisebyte.samarco.model.calendario.Calendario;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface CalendarioRepository extends CrudRepository<Calendario, Long> {

    @Query("SELECT c FROM Calendario c WHERE c.revisao.id = ?1")
    Optional<Calendario> findByRevisaoId(Long revisaoId);
}
