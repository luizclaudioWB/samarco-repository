package br.com.wisebyte.samarco.business.tarifa.fornecedor;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.tarifa.MontanteTarifaFornecedorDTO;
import br.com.wisebyte.samarco.mapper.fornecedor.FornecedorMapper;
import br.com.wisebyte.samarco.mapper.tarifa.TarifaFornecedorMapper;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaFornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static java.math.MathContext.DECIMAL64;

@ApplicationScoped
public class CalcMontanteEnergiaFornecedorUC {


    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TarifaFornecedorRepository tarifaFornecedorRepository;

    @Inject
    FornecedorMapper fornecedorMapper;

    @Inject
    TarifaFornecedorMapper tarifaFornecedorMapper;

    public QueryList<MontanteTarifaFornecedorDTO> calcMontanteEnergiaFornecedor( @NotNull Long revisaoId, @NotNull Long fornecedorId ) {
        Revisao revisao = revisaoRepository.findById( revisaoId ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Revisao", "Revisao", "Revisao Não Encontrada" ) );
        Fornecedor fornecedor = fornecedorRepository.findById( fornecedorId ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Fornecedor", "Fornecedor", "Fornecedor Não Encontrada" ) );
        List<MontanteTarifaFornecedorDTO> list = tarifaFornecedorRepository.findByFornecedor( fornecedor ).stream( )
                .filter( it -> it.getPlanejamento( ).getRevisao( ).getId( ).equals( revisao.getId( ) ) )
                .filter( it -> it.getFornecedor( ).getId( ).equals( fornecedorId ) )
                .map( this::calcTarifaFornecedorAtualizada ).toList( );
        return QueryList.<MontanteTarifaFornecedorDTO>builder( )
                .totalElements( ( long ) list.size( ) )
                .totalPages( 1L )
                .results( list )
                .build( );
    }

    private MontanteTarifaFornecedorDTO calcTarifaFornecedorAtualizada( TarifaFornecedor tarifaFornecedor ) {
        BigDecimal ipcaTotal = tarifaFornecedor.getIpcaRealizada( ).add( tarifaFornecedor.getIpcaProjetado( ), DECIMAL64 ).divide( BigDecimal.valueOf( 100 ), DECIMAL64 );
        BigDecimal precoBase = tarifaFornecedor.getFornecedor( ).getPrecoBase( );
        BigDecimal precoAtualizado = precoBase.multiply( ipcaTotal, DECIMAL64 ).add( precoBase, DECIMAL64 );
        return MontanteTarifaFornecedorDTO.builder( )
                .fornecedor( fornecedorMapper.toDTO( tarifaFornecedor.getFornecedor( ) ) )
                .tarifaFornecedor( tarifaFornecedorMapper.toDTO( tarifaFornecedor ) )
                .preco( precoAtualizado )
                .ipcaTotal( ipcaTotal )
                .valorMontante( precoAtualizado.multiply( tarifaFornecedor.getMontante( ), DECIMAL64 ) )
                .build( );
    }
}
