package br.com.wisebyte.samarco.graphql.query.unidade;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.unidade.QueryUnidadeUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UnidadeQuery {

    @Inject
    QueryUnidadeUC queryUnidadeUC;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    @Query( value = "unidades" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_UNITS} )
    public QueryList<UnidadeDTO> listarUnidades( @NotNull Integer page, @NotNull Integer size ) {
        return queryUnidadeUC.list( page, size );
    }

    @Query( value = "unidadePorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_UNIT_BY_ID} )
    public UnidadeDTO buscarUnidadePorId( Long id ) {
        return queryUnidadeUC.findById( id );
    }

    @Query( value = "listarUnidadesPorRecebedoraCreditosDeInjecao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_UNITS_BY_INJECTION_CREDIT_RECEIVER} )
    public List<UnidadeDTO> listarUnidadesPorRecebedoraCreditosDeInjecao( Long unidadeId ) {
        return unidadeRepository.findById( unidadeId )
                .map( unidade -> unidadeRepository.findByUnidadeCedenteCreditosDeInjecao( unidade ).stream( )
                        .map( unidadeMapper::toDTO )
                        .collect( Collectors.toList( ) )
                )
                .orElse( List.of( ) );
    }

    @Query( value = "filtrosPorUnidadesDisponiveis" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_UNITS_BY_INJECTION_CREDIT_RECEIVER} )
    public Set<FiltroUnidadesDisponiveis> filtrosPorUnidadesDisponiveis( ) {
        return Arrays.stream( FiltroUnidadesDisponiveis.values( ) ).collect( Collectors.toSet( ) );
    }

    public enum FiltroUnidadesDisponiveis {
        NAME
    }
}
