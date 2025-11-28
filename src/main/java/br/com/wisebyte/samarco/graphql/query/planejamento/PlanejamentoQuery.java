package br.com.wisebyte.samarco.graphql.query.planejamento;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoMapper;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.LISTAR_PLANEJAMENTO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;


@GraphQLApi
@RequestScoped
public class PlanejamentoQuery {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoMapper planejamentoMapper;


    @Query(value = "listarPlanejamentos")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_PLANEJAMENTO}
    )
    public List<PlanejamentoDTO> listarPlanejamentos() {
        return planejamentoRepository.findAll()
                .map(planejamentoMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Query(value = "buscarPlanejamentoPorId")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_PLANEJAMENTO}
    )
    public PlanejamentoDTO buscarPlanejamentoPorId(Long id) {
        return planejamentoRepository.findById(id)
                .map(planejamentoMapper::toDTO)
                .orElse(null);
    }

    @Query(value = "buscarPlanejamentoPorAno")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_PLANEJAMENTO}
    )
    public PlanejamentoDTO buscarPlanejamentoPorAno(Integer ano) {
        return planejamentoRepository.findByAno(ano)
                .map(planejamentoMapper::toDTO)
                .orElse(null);
    }


    @Query(value = "buscarPlanejamentoCorrente")
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_PLANEJAMENTO}
    )
    public PlanejamentoDTO buscarPlanejamentoCorrente() {
        return planejamentoRepository.findByCorrente(true)
                .map(planejamentoMapper::toDTO)
                .orElse(null);
    }
}
