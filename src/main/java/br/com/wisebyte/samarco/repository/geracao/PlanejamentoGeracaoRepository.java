package br.com.wisebyte.samarco.repository.geracao;

import br.com.wisebyte.samarco.model.geracao.PlanejamentoGeracao;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanejamentoGeracaoRepository extends BasicRepository<PlanejamentoGeracao, Long> {

    @Find
    List<PlanejamentoGeracao> findByRevisao(Revisao revisao);

    @Query("SELECT pg FROM PlanejamentoGeracao pg WHERE pg.revisao.id = :revisaoId")
    List<PlanejamentoGeracao> findByRevisaoId(Long revisaoId);

    @Find
    List<PlanejamentoGeracao> findByUnidade(Unidade unidade);

    @Find
    Optional<PlanejamentoGeracao> findByRevisaoAndUnidade(Revisao revisao, Unidade unidade);

    @Query("SELECT pg FROM PlanejamentoGeracao pg WHERE pg.revisao.id = :revisaoId AND pg.unidade.id = :unidadeId")
    Optional<PlanejamentoGeracao> findByRevisaoIdAndUnidadeId(Long revisaoId, Long unidadeId);
}
