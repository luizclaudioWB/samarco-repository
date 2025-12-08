package br.com.wisebyte.samarco.graphql.query.revisao;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.revisao.QueryRevisaoUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import static br.com.wisebyte.samarco.auth.Permissao.*;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class RevisaoQuery {

    @Inject
    QueryRevisaoUC queryRevisaoUC;

    @Query( value = "revisoes" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_REVISIONS} )
    public QueryList<RevisaoDTO> listarRevisoesPaginado( @NotNull Integer page, @NotNull Integer size ) {
        return queryRevisaoUC.list( page, size );
    }


    @Query( value = "revisaoPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_REVISION_BY_ID} )
    public RevisaoDTO buscarRevisaoPorId( Long id ) {
        return queryRevisaoUC.findById( id );
    }

    @Query( value = "revisoesOficiais" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_OFFICIAL_REVISIONS} )
    public QueryList<RevisaoDTO> listarRevisoesOficiais( ) {
        return queryRevisaoUC.listRevisoesOficiais( );
    }
}
