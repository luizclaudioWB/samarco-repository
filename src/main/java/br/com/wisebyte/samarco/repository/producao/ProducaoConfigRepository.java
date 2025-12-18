package br.com.wisebyte.samarco.repository.producao;


import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface ProducaoConfigRepository extends BasicRepository<ProducaoConfig, Long> {

    /**
     * Busca simples de ProducaoConfig por ID da revisão (sem carregar áreas).
     * Use este método para validações que não precisam acessar as áreas.
     */
    @Query("SELECT pc FROM ProducaoConfig pc WHERE pc.revisao.id = :revisaoId")
    Optional<ProducaoConfig> findByRevisaoId(Long revisaoId);

    /**
     * Busca ProducaoConfig por ID da revisão COM áreas carregadas (JOIN FETCH).
     * Use este método quando for converter para DTO ou precisar acessar as áreas.
     */
    @Query("SELECT pc FROM ProducaoConfig pc LEFT JOIN FETCH pc.areas WHERE pc.revisao.id = :revisaoId")
    Optional<ProducaoConfig> findByRevisaoIdComAreas(Long revisaoId);

    /**
     * Busca ProducaoConfig por ID COM áreas carregadas (JOIN FETCH).
     * Use este método quando for converter para DTO ou precisar acessar as áreas.
     */
    @Query("SELECT pc FROM ProducaoConfig pc LEFT JOIN FETCH pc.areas WHERE pc.id = :id")
    Optional<ProducaoConfig> findByIdComAreas(Long id);

    /**
     * Verifica se existe uma ProducaoConfig para a revisão especificada.
     */
    @Query("SELECT COUNT(pc) > 0 FROM ProducaoConfig pc WHERE pc.revisao.id = :revisaoId")
    boolean existsByRevisaoId(Long revisaoId);

    /**
     * Verifica se existe uma ProducaoConfig para a revisão especificada, excluindo um ID específico.
     * Útil para validação de unicidade em updates.
     */
    @Query("SELECT COUNT(pc) > 0 FROM ProducaoConfig pc WHERE pc.revisao.id = :revisaoId AND pc.id <> :excludeId")
    boolean existsByRevisaoIdExcludingId(Long revisaoId, Long excludeId);

    /**
     * Conta quantas áreas específicas estão associadas a uma ProducaoConfig.
     * Retorna > 0 se a área está na config, 0 caso contrário.
     */
    @Query("SELECT COUNT(a) FROM ProducaoConfig pc JOIN pc.areas a WHERE pc.revisao.id = :revisaoId AND a.id = :areaId")
    long countAreaInProducaoConfig(Long revisaoId, Long areaId);

    /**
     * Busca todas as ProducaoConfig COM áreas carregadas (JOIN FETCH).
     * Use este método quando for listar e converter para DTO.
     */
    @Query("SELECT DISTINCT pc FROM ProducaoConfig pc LEFT JOIN FETCH pc.areas")
    java.util.List<ProducaoConfig> findAllComAreas();
}
