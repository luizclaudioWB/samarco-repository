package br.com.wisebyte.samarco.repository.unidade;

import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface UnidadeRepository extends BasicRepository<Unidade, Long> {

    @Find
    List<Unidade> findByUnidadeCedenteCreditosDeInjecao( Unidade unidadeCedenteCreditosDeInjecao );

    @Find
    Page<Unidade> findByUnidadeGeradora( Boolean unidadeGeradora, PageRequest pageRequest, Order<Unidade> order );
}
