package br.com.wisebyte.samarco.repository.consumo;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.area.TipoArea;
import br.com.wisebyte.samarco.model.consumo.PlanejamentoConsumoEspecifico;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanejamentoConsumoEspecificoRepository extends BasicRepository<PlanejamentoConsumoEspecifico, Long> {

    @Find
    List<PlanejamentoConsumoEspecifico> findByRevisao(Revisao revisao);

    @Find
    Optional<PlanejamentoConsumoEspecifico> findByRevisaoAndArea(Revisao revisao, Area area);

    @Query("SELECT pce FROM PlanejamentoConsumoEspecifico pce WHERE pce.revisao.id = :revisaoId")
    List<PlanejamentoConsumoEspecifico> findByRevisaoId(Long revisaoId);

    @Query("SELECT pce FROM PlanejamentoConsumoEspecifico pce WHERE pce.revisao.id = :revisaoId AND pce.area.id = :areaId")
    Optional<PlanejamentoConsumoEspecifico> findByRevisaoIdAndAreaId(Long revisaoId, Long areaId);

    @Query("SELECT pce FROM PlanejamentoConsumoEspecifico pce WHERE pce.revisao.id = :revisaoId AND pce.area.tipoArea = :tipoArea")
    List<PlanejamentoConsumoEspecifico> findByRevisaoIdAndTipoArea(Long revisaoId, TipoArea tipoArea);
}
