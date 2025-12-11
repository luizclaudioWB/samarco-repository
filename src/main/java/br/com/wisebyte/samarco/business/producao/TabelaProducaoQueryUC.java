package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.area.AreaIdDTO;
import br.com.wisebyte.samarco.dto.prducao.TablePlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoProducaoMapper;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.math.MathContext.DECIMAL64;

@ApplicationScoped
public class TabelaProducaoQueryUC {

    @Inject
    ProducaoConfigRepository producaoConfigRepository;

    @Inject
    PlanejamentoProducaoRepository planejamentoProducaoRepository;

    @Inject
    PlanejamentoProducaoMapper mapper;

    public QueryList<TablePlanejamentoProducaoDTO> calcTabelaProducao( @NotNull Long revisaoId ) {
        ProducaoConfig config = producaoConfigRepository.findByRevisao_id( revisaoId );
        if ( config == null ) {
            throw new ValidadeExceptionBusiness( "ProducaoConfig", "ProducaoConfig", "Config não encontrada" );
        }
        Integer multiplicador = config.getMultiplicador( );
        List<TablePlanejamentoProducaoDTO> list = planejamentoProducaoRepository.findByRevisao( config.getRevisao( ) ).stream( )
                .map( it -> calcProducao( it, valueOf( multiplicador ) ) )
                .sorted( Comparator.comparing( it -> it.getPlanejamentoProducao( ).getAreaId( ) ) )
                .toList( );
        return QueryList.<TablePlanejamentoProducaoDTO>builder( )
                .totalElements( ( long ) list.size( ) )
                .totalPages( 1L )
                .results( list )
                .build( );
    }

    private TablePlanejamentoProducaoDTO calcProducao( PlanejamentoProducao it, BigDecimal multiplicador ) {
        return TablePlanejamentoProducaoDTO.builder( )
                .planejamentoProducao( mapper.toDTO( it ) )
                .area( AreaIdDTO.builder( ).id( it.getArea( ).getId( ) ).build( ) )
                .valorPlanejadoJaneiro( it.getValorJaneiro( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoFevereiro( it.getValorFevereiro( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoMarco( it.getValorMarco( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoAbril( it.getValorAbril( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoMaio( it.getValorMaio( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoJunho( it.getValorJunho( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoJulho( it.getValorJulho( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoAgosto( it.getValorAgosto( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoSetembro( it.getValorSetembro( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoOutubro( it.getValorOutubro( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoNovembro( it.getValorNovembro( ).multiply( multiplicador, DECIMAL64 ) )
                .valorPlanejadoDezembro( it.getValorDezembro( ).multiply( multiplicador, DECIMAL64 ) )
                .build( );
    }
}
