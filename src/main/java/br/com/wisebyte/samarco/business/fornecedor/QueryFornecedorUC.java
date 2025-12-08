package br.com.wisebyte.samarco.business.fornecedor;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.fornecedor._Fornecedor;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QueryFornecedorUC {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    public QueryList<FornecedorDTO> list( Integer page, Integer size ) {
        Page<Fornecedor> all = fornecedorRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _Fornecedor.nome.asc( ) ) );
        return QueryList.<FornecedorDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( fornecedorMapper::toDTO ).toList( ) )
                .build( );
    }

    public FornecedorDTO findById( Long id ) {
        return fornecedorRepository.findById( id ).map( fornecedorMapper::toDTO ).orElse( null );
    }
}
