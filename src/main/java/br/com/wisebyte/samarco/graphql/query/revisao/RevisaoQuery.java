package br.com.wisebyte.samarco.graphql.query.revisao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.mapper.revisao.RevisaoMapper;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.LISTAR_REVISAO;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class RevisaoQuery {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    @Query( value = "listarRevisoes" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_REVISAO} )
    public List<RevisaoDTO> listarRevisoes( ) {
        return revisaoRepository.findAll( )
                .map( revisaoMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarRevisaoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_REVISAO} )
    public RevisaoDTO buscarRevisaoPorId( Long id ) {
        return revisaoRepository.findById( id )
                .map( revisaoMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "listarRevisoesOficiais" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_REVISAO} )
    public List<RevisaoDTO> listarRevisoesOficiais( ) {
        return revisaoRepository.findByOficial( true ).stream( )
                .map( revisaoMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "listarRevisoesPorPlanejamento" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_REVISAO} )
    public List<RevisaoDTO> listarRevisoesPorPlanejamento( Long planejamentoId ) {
        return planejamentoRepository.findById( planejamentoId )
                .map( planejamento -> revisaoRepository.findByPlanejamento( planejamento ).stream( )
                        .map( revisaoMapper::toDTO )
                        .collect( Collectors.toList( ) )
                )
                .orElse( List.of( ) );
    }
}
