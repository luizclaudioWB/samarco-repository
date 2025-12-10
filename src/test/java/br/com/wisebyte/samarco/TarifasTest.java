package br.com.wisebyte.samarco;

import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.model.estado.Estado;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.planejamento.tarifa.AliquotaImpostos;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaPlanejamento;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

@QuarkusTest
public class TarifasTest {

    @Inject
    EntityManager entityManager;


    void cadastraDistribuidoras( ) {
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "ONS" )
                        .cnpj( "02.831.210/0001-57" )
                        .siglaAgente( "ONS" )
                        .estado( Estado.RJ )
                        .build( )
        );
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "EDP" )
                        .cnpj( "28.152.650/0001-71" )
                        .siglaAgente( "EDP" )
                        .estado( Estado.ES )
                        .build( )
        );
        entityManager.persist(
                Distribuidora.builder( )
                        .nome( "ENERGISA" )
                        .cnpj( "19.527.639/0001-58" )
                        .siglaAgente( "ENERGISA" )
                        .estado( Estado.MG )
                        .build( )
        );
    }

    void cadastraFornecedor( ) {
        entityManager.persist(
                Fornecedor.builder( )
                        .nome( "Cemig Convencional" )
                        .cnpj( "01.000.000/0001-00" )
                        .inicioDatabase( LocalDate.of( 2021, 5, 1 ) )
                        .precoBase( BigDecimal.valueOf( Double.parseDouble( "142.59" ) ) )
                        .estado( Estado.MG )
                        .build( )
        );
        entityManager.persist(
                Fornecedor.builder( )
                        .nome( "Cemig Incentivada" )
                        .cnpj( "01.000.000/0001-00" )
                        .inicioDatabase( LocalDate.of( 2023, 11, 1 ) )
                        .precoBase( BigDecimal.valueOf( Double.parseDouble( "152.88" ) ) )
                        .estado( Estado.MG )
                        .build( )
        );
        entityManager.persist(
                Fornecedor.builder( )
                        .nome( "Elera" )
                        .cnpj( "01.000.000/0001-00" )
                        .inicioDatabase( LocalDate.of( 2024, 2, 1 ) )
                        .precoBase( BigDecimal.valueOf( Double.parseDouble( "159.2" ) ) )
                        .estado( Estado.MG )
                        .build( )
        );
        entityManager.persist(
                Fornecedor.builder( )
                        .nome( "Matrix" )
                        .cnpj( "01.000.000/0001-00" )
                        .inicioDatabase( LocalDate.of( 2024, 4, 1 ) )
                        .precoBase( BigDecimal.valueOf( Double.parseDouble( "143.4" ) ) )
                        .estado( Estado.ES )
                        .build( )
        );
    }

    void cadastraTarifaPlanejamento( ) {
        Revisao revisao = entityManager.find( Revisao.class, 1L );
        entityManager.persist( TarifaPlanejamento.builder( )
                .revisao( revisao )
                .build( ) );
    }

    void cadastraAliquotaImpostos( ) {
        TarifaPlanejamento tarifaPlanejamento = entityManager.find( TarifaPlanejamento.class, 1L );
        AliquotaImpostos mg = AliquotaImpostos.builder( )
                .ano( Short.valueOf( "2026" ).intValue( ) )
                .planejamento( tarifaPlanejamento )
                .estado( Estado.MG )
                .percentualIcms( new BigDecimal( "18" ) )
                .percentualPis( new BigDecimal( "1.65" ) )
                .percentualCofins( new BigDecimal( "7.6" ) )
                .percentualIpca( new BigDecimal( "2.16" ) )
                .build( );
        entityManager.persist( mg );
        AliquotaImpostos es = AliquotaImpostos.builder( )
                .ano( Short.valueOf( "2026" ).intValue( ) )
                .planejamento( tarifaPlanejamento )
                .estado( Estado.ES )
                .percentualIcms( new BigDecimal( "17" ) )
                .percentualPis( new BigDecimal( "1.65" ) )
                .percentualCofins( new BigDecimal( "7.6" ) )
                .percentualIpca( new BigDecimal( "2.16" ) )
                .build( );
        entityManager.persist( es );
        AliquotaImpostos rj = AliquotaImpostos.builder( )
                .ano( Short.valueOf( "2026" ).intValue( ) )
                .planejamento( tarifaPlanejamento )
                .estado( Estado.RJ )
                .percentualIcms( new BigDecimal( "18" ) )
                .percentualPis( new BigDecimal( "1.65" ) )
                .percentualCofins( new BigDecimal( "7.6" ) )
                .percentualIpca( new BigDecimal( "2.16" ) )
                .build( );
        entityManager.persist( rj );
    }

    void cadastraTarifaDistribuidoraONS( ) {
        TarifaPlanejamento tarifaPlanejamento = entityManager.find( TarifaPlanejamento.class, 1L );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 1L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2025, 7, 1 ) )
                .periodoFinal( LocalDate.of( 2026, 6, 30 ) )
                .valorPonta( new BigDecimal( "7.10" ) )
                .valorForaPonta( new BigDecimal( "7.10" ) )
                .valorEncargos( new BigDecimal( "66.52" ) )
                .valorEncargosAutoProducao( BigDecimal.ZERO )
                .percentualPisCofins( new BigDecimal( "8.0" ) )
                .sobrescreverICMS( false )
                .build( ) );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 1L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2026, 7, 1 ) )
                .periodoFinal( LocalDate.of( 2027, 6, 30 ) )
                .valorPonta( new BigDecimal( "7.78" ) )
                .valorForaPonta( new BigDecimal( "7.82" ) )
                .valorEncargos( new BigDecimal( "60.66" ) )
                .valorEncargosAutoProducao( BigDecimal.ZERO )
                .percentualPisCofins( new BigDecimal( "8.0" ) )
                .sobrescreverICMS( false )
                .build( ) );
    }

    void cadastraTarifaDistribuidoraEDP( ) {
        TarifaPlanejamento tarifaPlanejamento = entityManager.find( TarifaPlanejamento.class, 1L );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 2L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2025, 8, 1 ) )
                .periodoFinal( LocalDate.of( 2026, 7, 31 ) )
                .valorPonta( new BigDecimal( "21.44" ) )
                .valorForaPonta( new BigDecimal( "9.83" ) )
                .valorEncargos( new BigDecimal( "90.88" ) )
                .valorEncargosAutoProducao( new BigDecimal( "13.33" ) )
                .percentualPisCofins( new BigDecimal( "7.0" ) )
                .sobrescreverICMS( false )
                .qtdeDeHorasPonta( 3 )
                .build( ) );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 2L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2026, 8, 1 ) )
                .periodoFinal( LocalDate.of( 2027, 7, 31 ) )
                .valorPonta( new BigDecimal( "21.06" ) )
                .valorForaPonta( new BigDecimal( "8.94" ) )
                .valorEncargos( new BigDecimal( "87.10" ) )
                .valorEncargosAutoProducao( new BigDecimal( "8.44" ) )
                .percentualPisCofins( new BigDecimal( "7.0" ) )
                .sobrescreverICMS( false )
                .qtdeDeHorasPonta( 3 )
                .build( ) );
    }

    void cadastraTarifaDistribuidoraEnergisa( ) {
        TarifaPlanejamento tarifaPlanejamento = entityManager.find( TarifaPlanejamento.class, 1L );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 3L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2025, 7, 1 ) )
                .periodoFinal( LocalDate.of( 2026, 6, 30 ) )
                .valorPonta( new BigDecimal( "15.96" ) )
                .valorForaPonta( new BigDecimal( "8.58" ) )
                .valorEncargos( new BigDecimal( "83.19" ) )
                .valorEncargosAutoProducao( new BigDecimal( "12.75" ) )
                .percentualPisCofins( new BigDecimal( "7.0" ) )
                .sobrescreverICMS( true )
                .percentualICMS( ZERO )
                .build( ) );
        entityManager.persist( TarifaDistribuidora.builder( )
                .distribuidora( entityManager.find( Distribuidora.class, 3L ) )
                .planejamento( tarifaPlanejamento )
                .periodoInicial( LocalDate.of( 2026, 7, 1 ) )
                .periodoFinal( LocalDate.of( 2027, 6, 30 ) )
                .valorPonta( new BigDecimal( "29.67" ) )
                .valorForaPonta( new BigDecimal( "11.69" ) )
                .valorEncargos( new BigDecimal( "84.43" ) )
                .valorEncargosAutoProducao( new BigDecimal( "9.99" ) )
                .percentualPisCofins( new BigDecimal( "7.0" ) )
                .sobrescreverICMS( true )
                .percentualICMS( ZERO )
                .build( ) );
    }

    void cadastraTarifaFornecedor( ) {
        TarifaPlanejamento tarifaPlanejamento = entityManager.find( TarifaPlanejamento.class, 1L );
        entityManager.persist( TarifaFornecedor.builder( )
                .planejamento( tarifaPlanejamento )
                .fornecedor( entityManager.find( Fornecedor.class, 1L ) )
                .ipcaRealizada( new BigDecimal( "27.60" ) )
                .ipcaProjetado( new BigDecimal( "2.35" ) )
                .montante( new BigDecimal( "80" ) )
                .build( ) );
        entityManager.persist( TarifaFornecedor.builder( )
                .planejamento( tarifaPlanejamento )
                .fornecedor( entityManager.find( Fornecedor.class, 2L ) )
                .ipcaRealizada( new BigDecimal( "8.74" ) )
                .ipcaProjetado( new BigDecimal( "1.99" ) )
                .montante( new BigDecimal( "10" ) )
                .build( ) );
        entityManager.persist( TarifaFornecedor.builder( )
                .planejamento( tarifaPlanejamento )
                .fornecedor( entityManager.find( Fornecedor.class, 3L ) )
                .ipcaRealizada( new BigDecimal( "6.79" ) )
                .ipcaProjetado( new BigDecimal( "1.96" ) )
                .montante( new BigDecimal( "20" ) )
                .build( ) );
        entityManager.persist( TarifaFornecedor.builder( )
                .planejamento( tarifaPlanejamento )
                .fornecedor( entityManager.find( Fornecedor.class, 4L ) )
                .ipcaRealizada( new BigDecimal( "6.22" ) )
                .ipcaProjetado( new BigDecimal( "1.95" ) )
                .montante( new BigDecimal( "20" ) )
                .build( ) );
    }

    void printValorPontaDistribuidora( ) {
        List<TarifaDistribuidora> distribuidoras = entityManager.createQuery( "select t from TarifaDistribuidora t", TarifaDistribuidora.class ).getResultList( );
        List<AliquotaImpostos> aliquotas = entityManager.createQuery( "select t from AliquotaImpostos t", AliquotaImpostos.class ).getResultList( );
        System.out.println( "PONTA" );
        System.out.println( "Valor Bruto Ponta | Valor ICMS | Valor PIS Ponta | Valor COFINS Ponta | LIQUIDO SAP" );
        Locale brasil = Locale.of( "pt", "BR" );
        DecimalFormatSymbols symbols = new DecimalFormatSymbols( brasil );
        final DecimalFormat df = new DecimalFormat( "#,##0.00", symbols );
        for ( TarifaDistribuidora tarifa : distribuidoras ) {
            StringBuilder sb = new StringBuilder( );
            BigDecimal valor = calculaValorPonta( tarifa.getValorPonta( ), tarifa, aliquotas );
            sb.append( String.format( "%18s", df.format( valor ) ) );
            BigDecimal icms = calculaValorICMS( tarifa, aliquotas, valor );
            sb.append( String.format( "%13s", df.format( icms ) ) );
            BigDecimal pis = calculaValorPIS( tarifa, aliquotas, valor );
            sb.append( String.format( "%18s", df.format( pis ) ) );
            BigDecimal cofins = calculaValorCOFINS( tarifa, aliquotas, valor );
            sb.append( String.format( "%20s", df.format( cofins ) ) );
            BigDecimal liquido = valor.subtract( icms ).subtract( pis ).subtract( cofins ).setScale( 2, RoundingMode.HALF_UP );
            sb.append( String.format( "%14s", df.format( liquido ) ) );
            System.out.println( sb );
        }
    }

    BigDecimal calculaValorPonta( BigDecimal valor, TarifaDistribuidora tarifa, List<AliquotaImpostos> aliquotas ) {
        AliquotaImpostos aliquota = aliquotas.stream( )
                .filter( it -> it.getAno( ).intValue( ) == tarifa.getPlanejamento( ).getRevisao( ).getPlanejamento( ).getAno( ).intValue( ) )
                .filter( it -> it.getEstado( ) == tarifa.getDistribuidora( ).getEstado( ) )
                .findFirst( ).orElse( null );
        BigDecimal icms = ONE.subtract( tarifa.isSobrescreverICMS( ) ? tarifa.getPercentualICMS( ) : aliquota.getPercentualIcms( ).divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 ) );
        BigDecimal pisCofins = ONE.subtract( tarifa.getPercentualPisCofins( ).divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 ) );
        return valor
                .divide( icms, MathContext.DECIMAL64 )
                .divide( pisCofins, MathContext.DECIMAL64 ).setScale( 2, RoundingMode.HALF_UP );
    }

    BigDecimal calculaValorICMS( TarifaDistribuidora tarifa, List<AliquotaImpostos> aliquotas, BigDecimal valor ) {
        AliquotaImpostos aliquota = aliquotas.stream( )
                .filter( it -> it.getAno( ).intValue( ) == tarifa.getPlanejamento( ).getRevisao( ).getPlanejamento( ).getAno( ).intValue( ) )
                .filter( it -> it.getEstado( ) == tarifa.getDistribuidora( ).getEstado( ) )
                .findFirst( ).orElse( null );
        return calculaValorImposto( valor, (tarifa.isSobrescreverICMS( ) ? tarifa.getPercentualICMS( ) : aliquota.getPercentualIcms( )) );
    }

    BigDecimal calculaValorPIS( TarifaDistribuidora tarifa, List<AliquotaImpostos> aliquotas, BigDecimal valor ) {
        AliquotaImpostos aliquota = aliquotas.stream( )
                .filter( it -> it.getAno( ).intValue( ) == tarifa.getPlanejamento( ).getRevisao( ).getPlanejamento( ).getAno( ).intValue( ) )
                .filter( it -> it.getEstado( ) == tarifa.getDistribuidora( ).getEstado( ) )
                .findFirst( ).orElse( null );
        return calculaValorImposto( valor, aliquota.getPercentualPis( ) );
    }

    BigDecimal calculaValorCOFINS( TarifaDistribuidora tarifa, List<AliquotaImpostos> aliquotas, BigDecimal valor ) {
        AliquotaImpostos aliquota = aliquotas.stream( )
                .filter( it -> it.getAno( ).intValue( ) == tarifa.getPlanejamento( ).getRevisao( ).getPlanejamento( ).getAno( ).intValue( ) )
                .filter( it -> it.getEstado( ) == tarifa.getDistribuidora( ).getEstado( ) )
                .findFirst( ).orElse( null );
        return calculaValorImposto( valor, aliquota.getPercentualCofins( ) );
    }

    public BigDecimal calculaValorImposto( BigDecimal valor, BigDecimal imposto ) {
        BigDecimal percentual = imposto.divide( BigDecimal.valueOf( 100 ), MathContext.DECIMAL64 );
        return valor.multiply( percentual ).setScale( 2, RoundingMode.HALF_UP );
    }

    void printValores( ) {
        List<TarifaDistribuidora> distribuidoras = entityManager.createQuery( "select t from TarifaDistribuidora t", TarifaDistribuidora.class ).getResultList( );
        List<AliquotaImpostos> aliquotas = entityManager.createQuery( "select t from AliquotaImpostos t", AliquotaImpostos.class ).getResultList( );
        printValores( "PONTA", distribuidoras, aliquotas, TarifaDistribuidora::getValorPonta );
        printValores( "FORA PONTA", distribuidoras, aliquotas, TarifaDistribuidora::getValorForaPonta );
        printValores( "ENCARGO", distribuidoras, aliquotas, TarifaDistribuidora::getValorEncargos );
    }

    void printValores( String header, List<TarifaDistribuidora> distribuidoras, List<AliquotaImpostos> aliquotas, Function<TarifaDistribuidora, BigDecimal> fnGetValue ) {
        System.out.println( header );
        System.out.println( "Valor Bruto Ponta | Valor ICMS | Valor PIS Ponta | Valor COFINS Ponta | LIQUIDO SAP" );
        Locale brasil = Locale.of( "pt", "BR" );
        DecimalFormatSymbols symbols = new DecimalFormatSymbols( brasil );
        final DecimalFormat df = new DecimalFormat( "#,##0.00", symbols );
        for ( TarifaDistribuidora tarifa : distribuidoras ) {
            StringBuilder sb = new StringBuilder( );
            BigDecimal valor = calculaValorPonta( fnGetValue.apply( tarifa ), tarifa, aliquotas );
            sb.append( String.format( "%18s", df.format( valor ) ) );
            BigDecimal icms = calculaValorICMS( tarifa, aliquotas, valor );
            sb.append( String.format( "%13s", df.format( icms ) ) );
            BigDecimal pis = calculaValorPIS( tarifa, aliquotas, valor );
            sb.append( String.format( "%18s", df.format( pis ) ) );
            BigDecimal cofins = calculaValorCOFINS( tarifa, aliquotas, valor );
            sb.append( String.format( "%20s", df.format( cofins ) ) );
            BigDecimal liquido = valor.subtract( icms ).subtract( pis ).subtract( cofins ).setScale( 2, RoundingMode.HALF_UP );
            sb.append( String.format( "%14s", df.format( liquido ) ) );
            System.out.println( sb );
        }
    }

    @Test
    @Transactional
    public void testeTarifas( ) throws IOException {
//        cadastraDistribuidoras( );
//        cadastraFornecedor( );
//        cadastraTarifaPlanejamento( );
//        cadastraAliquotaImpostos( );
//        cadastraTarifaDistribuidoraONS( );
//        cadastraTarifaDistribuidoraEDP( );
//        cadastraTarifaDistribuidoraEnergisa( );
//        cadastraTarifaFornecedor( );
        printValores( );
    }
}
