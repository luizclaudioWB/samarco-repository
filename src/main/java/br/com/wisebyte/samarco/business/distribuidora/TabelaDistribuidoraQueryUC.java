package br.com.wisebyte.samarco.business.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.business.tarifa.distribuidora.CalcComponentesTarifariosTarifaDistribuidoraUC;
import br.com.wisebyte.samarco.dto.distribuidora.TabelaDistribuidoraLineDTO;
import br.com.wisebyte.samarco.dto.tarifa.ComponenteTarifarioDTO;
import br.com.wisebyte.samarco.dto.tarifa.TipoComponenteTarifarioDTO;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.mapper.unidade.UnidadeMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.wisebyte.samarco.dto.tarifa.TipoComponenteTarifarioDTO.*;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL64;
import static java.time.Month.*;

@ApplicationScoped
public class TabelaDistribuidoraQueryUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    TarifaDistribuidoraRepository tarifaDistribuidoraRepository;

    @Inject
    AliquotaImpostosRepository aliquotaRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    CalcComponentesTarifariosTarifaDistribuidoraUC calcComponentesTarifariosUC;

    @Inject
    UnidadeMapper unidadeMapper;


    public List<TabelaDistribuidoraLineDTO> calcTabelaDistribuidora( @NotNull Long revisaoId, @NotNull Long unidadeId ) {
        Revisao revisao = revisaoRepository.findById( revisaoId ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Revisao", "Revisao", "Revisao Não Encontrada" ) );
        Unidade unidade = unidadeRepository.findById( unidadeId )
                .stream( ).filter( it -> it.getUnidadeGeradora( ) != null && it.getUnidadeGeradora( ) == false )
                .findFirst( )
                .orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Unidade", "Unidade", "Unidade Não Encontrada ou Unidade não válida." ) );
        List<TarifaDistribuidora> tarifas = tarifaDistribuidoraRepository.findByRevisao( revisao );
        if ( tarifas.isEmpty( ) ) {
            return null;
        }
        Set<AliquotaImpostos> aliquotas = aliquotaRepository.findByTarifaPlanejamento( tarifas.iterator( ).next( ).getPlanejamento( ) )
                .stream( ).collect( Collectors.toSet( ) );
        return calcTableLine( unidade, tarifas, aliquotas );
    }

    private List<TabelaDistribuidoraLineDTO> calcTableLine( Unidade unidade, List<TarifaDistribuidora> tarifas, Set<AliquotaImpostos> aliquotas ) {
        List<TarifaDistribuidora> tarifasToUse = tarifas.stream( ).filter( it -> unidade.getConectadaRedeBasica( ).equals( Boolean.TRUE ) ?
                        it.getDistribuidora( ).getSiglaAgente( ).equals( "ONS" ) : it.getDistribuidora( ).getEstado( ).equals( unidade.getEstado( ) ) )
                .sorted( Comparator.comparing( TarifaDistribuidora::getPeriodoInicial ) )
                .toList( );
        AliquotaImpostos aliquota = aliquotas.stream( ).filter( it -> it.getEstado( ) == unidade.getEstado( ) ).findFirst( ).orElse( null );
        if ( tarifasToUse.size( ) != 2 ) {
            return null;
        }
        TarifaDistribuidora firstPeriod = tarifasToUse.get( 0 );
        TarifaDistribuidora lastPeriod = tarifasToUse.get( 1 );
        UnidadeDTO unidadeDTO = unidadeMapper.toDTO( unidade );
        TabelaDistribuidoraLineDTO ponta = buildLine( unidadeDTO, PONTA, TabelaDistribuidoraLineDTO.HorarioDTO.PONTA, firstPeriod, lastPeriod, aliquota, TarifaDistribuidora::getValorPonta, unidade.getConectadaRedeBasica( ) == true );
        TabelaDistribuidoraLineDTO foraPonta = buildLine( unidadeDTO, FORA_PONTA, TabelaDistribuidoraLineDTO.HorarioDTO.FORA_PONTA, firstPeriod, lastPeriod, aliquota, TarifaDistribuidora::getValorPonta,
                unidade.getConectadaRedeBasica( ) == true );
        TabelaDistribuidoraLineDTO encargo = buildLine( unidadeDTO, ENCARGO, TabelaDistribuidoraLineDTO.HorarioDTO.ENCARGO_TRANSMISSAO, firstPeriod, lastPeriod, aliquota, TarifaDistribuidora::getValorPonta,
                unidade.getConectadaRedeBasica( ) == true );
        TabelaDistribuidoraLineDTO descontoGA = buildDescontoGA( unidadeDTO, firstPeriod, lastPeriod );
        TabelaDistribuidoraLineDTO encargoDistribuidora = buildLine( unidadeDTO, ENCARGO, TabelaDistribuidoraLineDTO.HorarioDTO.ENCARGO_DISTRIBUICAO, firstPeriod, lastPeriod, aliquota, TarifaDistribuidora::getValorPonta,
                unidade.getDistribuidora( ) == null );
        return Arrays.asList( ponta, foraPonta, encargo, descontoGA, encargoDistribuidora );
    }

    TabelaDistribuidoraLineDTO buildLine( UnidadeDTO unidade, TipoComponenteTarifarioDTO tipo, TabelaDistribuidoraLineDTO.HorarioDTO horario, TarifaDistribuidora firstPeriod, TarifaDistribuidora lastPeriod, AliquotaImpostos aliquota,
                                          Function<TarifaDistribuidora, BigDecimal> getValue, Boolean isZero ) {
        ComponenteTarifarioDTO valuesPontaFirstPeriod = calcComponentesTarifariosUC.calcComponenteTarifarios( tipo, firstPeriod, aliquota, getValue.apply( firstPeriod ) );
        ComponenteTarifarioDTO valuesPontaLastPeriod = calcComponentesTarifariosUC.calcComponenteTarifarios( tipo, lastPeriod, aliquota, getValue.apply( lastPeriod ) );
        BigDecimal valueFirst = valuesPontaFirstPeriod.getLiquidoSAP( );
        BigDecimal valueLast = valuesPontaLastPeriod.getLiquidoSAP( );
        return buildLine( unidade, horario, isZero, valueFirst, valueLast, lastPeriod.getPeriodoInicial( ) );
    }

    TabelaDistribuidoraLineDTO buildDescontoGA( UnidadeDTO unidade, TarifaDistribuidora firstPeriod, TarifaDistribuidora lastPeriod ) {
        boolean withDiscount = unidade.getUnidadeCedenteCreditosDeInjecao( ) != null;
        BigDecimal valueFirst = firstPeriod.getValorEncargos( ).subtract( firstPeriod.getValorEncargosAutoProducao( ), DECIMAL64 );
        BigDecimal valueLast = lastPeriod.getValorEncargos( ).subtract( lastPeriod.getValorEncargosAutoProducao( ), DECIMAL64 );
        return buildLine( unidade, TabelaDistribuidoraLineDTO.HorarioDTO.ENCARGO_DISTRIBUICAO, withDiscount, valueFirst, valueLast, lastPeriod.getPeriodoInicial( ) );
    }

    TabelaDistribuidoraLineDTO buildLine( UnidadeDTO unidade, TabelaDistribuidoraLineDTO.HorarioDTO horario, Boolean zeroValue, BigDecimal valueFirst, BigDecimal valueLast, LocalDate startDate ) {
        return TabelaDistribuidoraLineDTO.builder( )
                .unidade( unidade )
                .servico( horario.getServicoDTO( ) )
                .horario( horario )
                .valorJaneiro( zeroValue ? ZERO : startDate.getMonthValue( ) < JANUARY.getValue( ) ? valueFirst : valueLast )
                .valorFevereiro( zeroValue ? ZERO : startDate.getMonthValue( ) < FEBRUARY.getValue( ) ? valueFirst : valueLast )
                .valorMarco( zeroValue ? ZERO : startDate.getMonthValue( ) < MARCH.getValue( ) ? valueFirst : valueLast )
                .valorAbril( zeroValue ? ZERO : startDate.getMonthValue( ) < APRIL.getValue( ) ? valueFirst : valueLast )
                .valorMaio( zeroValue ? ZERO : startDate.getMonthValue( ) < MAY.getValue( ) ? valueFirst : valueLast )
                .valorJunho( zeroValue ? ZERO : startDate.getMonthValue( ) < JUNE.getValue( ) ? valueFirst : valueLast )
                .valorJulho( zeroValue ? ZERO : startDate.getMonthValue( ) < JULY.getValue( ) ? valueFirst : valueLast )
                .valorAgosto( zeroValue ? ZERO : startDate.getMonthValue( ) < AUGUST.getValue( ) ? valueFirst : valueLast )
                .valorSetembro( zeroValue ? ZERO : startDate.getMonthValue( ) < SEPTEMBER.getValue( ) ? valueFirst : valueLast )
                .valorOutubro( zeroValue ? ZERO : startDate.getMonthValue( ) < OCTOBER.getValue( ) ? valueFirst : valueLast )
                .valorNovembro( zeroValue ? ZERO : startDate.getMonthValue( ) < NOVEMBER.getValue( ) ? valueFirst : valueLast )
                .valorDezembro( zeroValue ? ZERO : startDate.getMonthValue( ) < DECEMBER.getValue( ) ? valueFirst : valueLast )
                .build( );
    }

}
