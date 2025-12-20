package br.com.wisebyte.samarco.repository.consumo;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.consumo.ConsumoEspecifico;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ConsumoEspecificoRepository extends BasicRepository<ConsumoEspecifico, Long> {

    @Find
    Stream<ConsumoEspecifico> findAll();

    @Find
    List<ConsumoEspecifico> findByRevisao(Revisao revisao);

    @Find
    List<ConsumoEspecifico> findByArea(Area area);

    @Find
    Optional<ConsumoEspecifico> findByRevisaoAndArea(Revisao revisao, Area area);
}
