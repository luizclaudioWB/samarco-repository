package br.com.wisebyte.samarco.repository.producao;


import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

@Repository
public interface ProducaoConfigRepository extends BasicRepository<ProducaoConfig, Long> {

    @Find
    ProducaoConfig findByRevisao_id( Long revisao_id );
}
