package br.com.wisebyte.samarco.repository.planejamento;

import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PlanejamentoRepository extends BasicRepository<Planejamento, Long> {

    @Find
    Optional<Planejamento> findByAno(Integer ano);

    @Find
    Optional<Planejamento> findByCorrente(Boolean corrente);

    @Find
    Stream<Planejamento> findAll();
}
