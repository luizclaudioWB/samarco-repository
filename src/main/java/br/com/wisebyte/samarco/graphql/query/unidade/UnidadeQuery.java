package br.com.wisebyte.samarco.graphql.query.unidade;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class UnidadeQuery {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UnidadeMapper unidadeMapper;

    @Query( value = "listarUnidades" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_UNITS} )
    public List<UnidadeDTO> listarUnidades( ) {
        return unidadeRepository.findAll( )
                .map( unidadeMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarUnidadePorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_UNIT_BY_ID} )
    public UnidadeDTO buscarUnidadePorId( Long id ) {
        return unidadeRepository.findById( id )
                .map( unidadeMapper::toDTO )
                .orElse( null );
    }

    @Query( value = "listarUnidadesPorRecebedoraCreditosDeInjecao" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_UNITS_BY_INJECTION_CREDIT_RECEIVER} )
    public List<UnidadeDTO> listarUnidadesPorRecebedoraCreditosDeInjecao( Long unidadeId ) {
        return unidadeRepository.findById( unidadeId )
                .map( unidade -> unidadeRepository.findByUnidadeRecebedoraCreditosDeInjecao( unidade ).stream( )
                        .map( unidadeMapper::toDTO )
                        .collect( Collectors.toList( ) )
                )
                .orElse( List.of( ) );
    }
}
