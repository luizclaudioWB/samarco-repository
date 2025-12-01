package br.com.wisebyte.samarco.graphql.query.fornecedor;

import br.com.wisebyte.samarco.annotation.SecuredAccess;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.auth.Permissao.LISTAR_FORNECEDOR;
import static br.com.wisebyte.samarco.auth.Role.ADMIN;

@GraphQLApi
@RequestScoped
public class FornecedorQuery {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    @Query( value = "listarFornecedores" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_FORNECEDOR} )
    public List<FornecedorDTO> listarFornecedores( ) {
        return fornecedorRepository.findAll( )
                .map( fornecedorMapper::toDTO )
                .collect( Collectors.toList( ) );
    }

    @Query( value = "buscarFornecedorPorId" )
    @SecuredAccess(
            roles = {ADMIN},
            permissionsRequired = {LISTAR_FORNECEDOR} )
    public FornecedorDTO buscarFornecedorPorId( Long id ) {
        return fornecedorRepository.findById( id )
                .map( fornecedorMapper::toDTO )
                .orElse( null );
    }
}
