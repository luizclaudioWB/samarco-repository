package br.com.wisebyte.samarco.repository.tarifa;

import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.Optional;
import java.util.stream.Stream;


@Repository
public interface TarifaPlanejamentoRepository extends BasicRepository<TarifaPlanejamento, Long> {

    @Find
    Optional<TarifaPlanejamento> findByRevisao(Revisao revisao);

    @Find
    Stream<TarifaPlanejamento> findAll();
}
