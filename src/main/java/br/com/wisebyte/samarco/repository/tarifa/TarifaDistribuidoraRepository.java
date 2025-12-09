package br.com.wisebyte.samarco.repository.tarifa;

import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface TarifaDistribuidoraRepository extends BasicRepository<TarifaDistribuidora, Long> {

    @Find
    List<TarifaDistribuidora> findByTarifaPlanejamento( TarifaPlanejamento planejamento );

    @Find
    List<TarifaDistribuidora> findByDistribuidora(Distribuidora distribuidora);

    @Find
    List<TarifaDistribuidora> findByPlanejamentoAndDistribuidora(
            TarifaPlanejamento planejamento,
            Distribuidora distribuidora
    );
}
