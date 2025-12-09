package br.com.wisebyte.samarco.business.tarifa;

import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaFornecedorMapper;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import br.com.wisebyte.samarco.model.planejamento.tarifa._TarifaFornecedor;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class QueryTarifaFornecedorUC {

    @Inject
    TarifaFornecedorRepository tarifaFornecedorRepository;

    @Inject
    TarifaFornecedorMapper tarifaFornecedorMapper;

    public QueryList<TarifaFornecedorDTO> list( @NotNull Integer page, @NotNull Integer size ) {
        Page<TarifaFornecedor> all = tarifaFornecedorRepository.findAll( PageRequest.ofPage( page, size, true ), Order.by( _TarifaFornecedor.id.desc( ) ) );
        return QueryList.<TarifaFornecedorDTO>builder( )
                .totalElements( all.totalElements( ) )
                .totalPages( all.totalPages( ) )
                .results( all.content( ).stream( ).map( tarifaFornecedorMapper::toDTO ).toList( ) )
                .build( );
    }

    public TarifaFornecedorDTO findById( Long id ) {
        return tarifaFornecedorRepository.findById( id ).map( tarifaFornecedorMapper::toDTO ).orElse( null );
    }

    public QueryList<TarifaFornecedorDTO> findByTarifaPlanejamento( @NotNull Long tarifaPlanejamentoId ) {
        return tarifaFornecedorMapper.toQueryDTO( tarifaFornecedorRepository.findByTarifaPlanejamento( TarifaPlanejamento.builder( ).id( tarifaPlanejamentoId ).build( ) ) );
    }

    public QueryList<TarifaFornecedorDTO> findByFornecedor( @NotNull Long fornecedorId ) {
        return tarifaFornecedorMapper.toQueryDTO( tarifaFornecedorRepository.findByFornecedor( Fornecedor.builder( ).id( fornecedorId ).build( ) ) );
    }
}
