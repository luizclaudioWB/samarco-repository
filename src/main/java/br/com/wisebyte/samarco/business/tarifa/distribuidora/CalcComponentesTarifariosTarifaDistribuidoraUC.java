package br.com.wisebyte.samarco.business.tarifa.distribuidora;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.tarifa.ComponenteTarifarioDTO;
import br.com.wisebyte.samarco.dto.tarifa.TipoComponenteTarifarioDTO;
import br.com.wisebyte.samarco.mapper.distribuidora.DistribuidoraMapper;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.tarifa.AliquotaImpostosRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaDistribuidoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.math.BigDecimal.ONE;

@ApplicationScoped
public class CalcComponentesTarifariosTarifaDistribuidoraUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Inject
    TarifaDistribuidoraRepository tarifaDistribuidoraRepository;

    @Inject
    AliquotaImpostosRepository aliquotaRepository;

    @Inject
    DistribuidoraMapper distribuidoraMapper;

    public List<ComponenteTarifarioDTO> calcComponentesTarifarios( @NotNull Long revisaoId, @NotNull Long distribuidoraId ) {
        Revisao revisao = revisaoRepository.findById( revisaoId ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Revisao", "Revisao", "Revisao Não Encontrada" ) );
        Distribuidora distribuidora = distribuidoraRepository.findById( distribuidoraId ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Distribuidora", "Distribuidora", "Distribuidora Não Encontrada" ) );
        List<TarifaDistribuidora> tarifas = tarifaDistribuidoraRepository.findByDistribuidora( distribuidora )
                .stream( ).filter( it -> it.getPlanejamento( ).getRevisao( ).getId( ).equals( revisao.getId( ) ) )
                .toList( );
        AliquotaImpostos aliquotaImpostos = aliquotaRepository.findByTarifaPlanejamento( tarifas.iterator( ).next( ).getPlanejamento( ) )
                .stream( ).filter( it -> it.getEstado( ) == distribuidora.getEstado( ) )
                .findFirst( ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Aliquota Impostos", "Aliquota Impostos", "Aliquota Impostos Não Encontrada" ) );
        return tarifas.stream( ).map( it -> new ArrayList<ComponenteTarifarioDTO>( ) {{
                    calcComponenteTarifarios( TipoComponenteTarifarioDTO.PONTA, it, aliquotaImpostos, it.getValorPonta( ) );
                    calcComponenteTarifarios( TipoComponenteTarifarioDTO.FORA_PONTA, it, aliquotaImpostos, it.getValorForaPonta( ) );
                    calcComponenteTarifarios( TipoComponenteTarifarioDTO.ENCARGO, it, aliquotaImpostos, it.getValorEncargos( ) );
                }} ).flatMap( Collection::stream )
                .sorted( Comparator.comparing( ComponenteTarifarioDTO::getInicioVigencia ) )
                .toList( );
    }

    ComponenteTarifarioDTO calcComponenteTarifarios( @NotNull TipoComponenteTarifarioDTO componente, @NotNull TarifaDistribuidora tarifa, @NotNull AliquotaImpostos aliquota, @NotNull BigDecimal value ) {
        BigDecimal valueWithTaxes = calcTotalValueWithTaxes( tarifa, aliquota, value );
        BigDecimal icms = calcTaxValue( tarifa.getValorPonta( ), (tarifa.isSobrescreverICMS( ) ? tarifa.getPercentualICMS( ) : aliquota.getPercentualIcms( )) );
        BigDecimal pis = calcTaxValue( tarifa.getValorPonta( ), aliquota.getPercentualPis( ) );
        BigDecimal cofins = calcTaxValue( tarifa.getValorPonta( ), aliquota.getPercentualCofins( ) );
        BigDecimal valueWithOutTaxes = valueWithTaxes.subtract( icms ).subtract( pis ).subtract( cofins ).setScale( 2, RoundingMode.HALF_UP );
        return ComponenteTarifarioDTO.builder( )
                .distribuidora( distribuidoraMapper.toDTO( tarifa.getDistribuidora( ) ) )
                .inicioVigencia( tarifa.getPeriodoInicial( ) )
                .fimVigencia( tarifa.getPeriodoFinal( ) )
                .componente( componente )
                .valorBruto( valueWithTaxes )
                .valorICMS( icms )
                .valorPis( pis )
                .valorCofins( cofins )
                .liquidoSAP( valueWithOutTaxes )
                .build( );
    }


    BigDecimal calcTotalValueWithTaxes( TarifaDistribuidora tarifa, AliquotaImpostos aliquota, BigDecimal value ) {
        BigDecimal icms = ONE.subtract( tarifa.isSobrescreverICMS( ) ? tarifa.getPercentualICMS( ) : aliquota.getPercentualIcms( ).divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 ) );
        BigDecimal pisCofins = ONE.subtract( tarifa.getPercentualPisCofins( ).divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 ) );
        return value
                .divide( icms, MathContext.DECIMAL64 )
                .divide( pisCofins, MathContext.DECIMAL64 ).setScale( 2, RoundingMode.HALF_UP );
    }


    BigDecimal calcTaxValue( BigDecimal value, BigDecimal percentage ) {
        BigDecimal percentual = percentage.divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 );
        return value.multiply( percentual ).setScale( 2, RoundingMode.HALF_UP );
    }
}
