package br.com.wisebyte.samarco.repository.demanda;

import br.com.wisebyte.samarco.model.demanda.Demanda;
import br.com.wisebyte.samarco.model.demanda.TipoHorario;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandaRepository extends BasicRepository<Demanda, Long> {

    @Find
    List<Demanda> findByRevisao(Revisao revisao);

    @Query("SELECT d FROM Demanda d WHERE d.revisao.id = :revisaoId")
    List<Demanda> findByRevisaoId(Long revisaoId);

    @Query("SELECT d FROM Demanda d WHERE d.revisao.id = :revisaoId AND d.unidade.id = :unidadeId")
    List<Demanda> findByRevisaoIdAndUnidadeId(Long revisaoId, Long unidadeId);

    @Find
    Optional<Demanda> findByRevisaoAndUnidadeAndTipoHorario(Revisao revisao, Unidade unidade, TipoHorario tipoHorario);
}
