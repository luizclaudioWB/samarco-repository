package br.com.wisebyte.samarco.repository.tarifa;

import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AliquotaImpostosRepository extends BasicRepository<AliquotaImpostos, Long> {

    @Find
    List<AliquotaImpostos> findByPlanejamento(TarifaPlanejamento planejamento);

    @Find
    Optional<AliquotaImpostos> findByPlanejamentoAndAnoAndEstado(
            TarifaPlanejamento planejamento,
            Integer ano,
            Estado estado
    );

    @Find
    List<AliquotaImpostos> findByEstado(Estado estado);

    @Find
    List<AliquotaImpostos> findByAno(Integer ano);
}
