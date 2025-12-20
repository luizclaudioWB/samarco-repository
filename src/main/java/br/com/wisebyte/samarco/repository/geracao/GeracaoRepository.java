package br.com.wisebyte.samarco.repository.geracao;

import br.com.wisebyte.samarco.model.geracao.Geracao;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface GeracaoRepository extends BasicRepository<Geracao, Long> {

    @Find
    Stream<Geracao> findAll();

    @Find
    List<Geracao> findByRevisao(Revisao revisao);

    @Find
    List<Geracao> findByUnidade(Unidade unidade);

    @Find
    Optional<Geracao> findByRevisaoAndUnidade(Revisao revisao, Unidade unidade);
}
