package br.com.wisebyte.samarco.repository.producao;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanejamentoProducaoRepository extends BasicRepository<PlanejamentoProducao, Long> {

    @Find
    List<PlanejamentoProducao> findByRevisao(Revisao revisao);

//    @Find
//    List<PlanejamentoProducao> findByArea(Area area);

    @Find
    Optional<PlanejamentoProducao> findByRevisaoAndArea(
            Revisao revisao,
            Area area
    );
}

