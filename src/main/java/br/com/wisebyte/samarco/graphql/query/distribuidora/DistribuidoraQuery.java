package br.com.wisebyte.samarco.graphql.query.distribuidora;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.LISTAR_DISTRIBUIDORA;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class DistribuidoraQuery {

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    @Query( value = "listarDistribuidoras" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_DISTRIBUIDORA} )
    public List<DistribuidoraDTO> listarDistribuidoras( ) {
        return distribuidoraRepository.findAll( )
                .map( distribuidoraMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarDistribuidoraPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_DISTRIBUIDORA} )
    public DistribuidoraDTO buscarDistribuidoraPorId( Long id ) {
        return distribuidoraRepository.findById( id )
                .map( distribuidoraMapper::toDTO )
                .orElse( null );
    }
}
