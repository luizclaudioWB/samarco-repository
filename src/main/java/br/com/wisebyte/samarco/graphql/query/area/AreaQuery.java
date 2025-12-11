package br.com.wisebyte.samarco.graphql.query.area;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.business.area.QueryAreaUC;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.dto.area.AreaIdDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import static br.com.wisebyte.samarco.auth.Permissao.GET_AREA_BY_ID;
import static br.com.wisebyte.samarco.auth.Permissao.LIST_AREAS;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class AreaQuery {

    @Inject
    QueryAreaUC queryAreaUC;

    @Query( value = "areas" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LIST_AREAS} )
    public QueryList<AreaDTO> listarAreas( @NotNull Integer page, @NotNull Integer size ) {
        return queryAreaUC.list( page, size );
    }

    @Query( value = "areaPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {GET_AREA_BY_ID} )
    public AreaDTO buscarAreaPorId( Long id ) {
        return queryAreaUC.findById( id );
    }

    public AreaDTO area( @Source AreaIdDTO areaId ) {
        if ( areaId == null || areaId.getId( ) == null ) {
            return null;
        }
        return queryAreaUC.findById( areaId.getId( ) );
    }
}
