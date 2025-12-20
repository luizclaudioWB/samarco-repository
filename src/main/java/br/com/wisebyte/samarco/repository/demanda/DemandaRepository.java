package br.com.wisebyte.samarco.repository.demanda;

import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.model.demanda.TipoHorario;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DemandaRepository extends BasicRepository<Demanda, Long> {

    @Find
    Stream<Demanda> findAll();

    @Find
    List<Demanda> findByRevisao(Revisao revisao);

    @Find
    List<Demanda> findByUnidade(Unidade unidade);

    @Find
    List<Demanda> findByHorario(TipoHorario horario);

    @Find
    Optional<Demanda> findByRevisaoAndUnidadeAndHorario(
        Revisao revisao,
        Unidade unidade,
        TipoHorario horario
    );
}
