package br.com.wisebyte.samarco.repository.revisao;

import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface RevisaoRepository extends BasicRepository<Revisao, Long> {

    @Find
    Stream<Revisao> findAll( );

    @Find
    List<Revisao> findByPlanejamento( Planejamento planejamento );

    @Find
    List<Revisao> findByUsuario( Usuario usuario );

    @Find
    List<Revisao> findByOficial( boolean oficial );

    @Find
    Optional<Revisao> findByPlanejamentoAndNumeroRevisao( Planejamento planejamento, Integer numeroRevisao );
}
