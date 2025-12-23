package br.com.wisebyte.samarco.dto.custo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Resultado do cálculo de Distribuição de Carga.
 * Baseado na Sheet 13 da planilha.
 *
 * Separa consumo, geração, perdas e necessidade de compra por estado.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistribuicaoCargaResultDTO {

    private Long revisaoId;

    // Dados de Minas Gerais
    private CargaEstadoDTO minasGerais;

    // Dados de Espírito Santo
    private CargaEstadoDTO espiritoSanto;

    // Totais Samarco (MG + ES)
    private CargaEstadoDTO totalSamarco;

    /** Percentual de perda de transmissão usado (ex: 0.03 = 3%) */
    private BigDecimal percentualPerda;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CargaEstadoDTO {

        // Valores mensais de consumo (MWh)
        private BigDecimal consumoJaneiro;
        private BigDecimal consumoFevereiro;
        private BigDecimal consumoMarco;
        private BigDecimal consumoAbril;
        private BigDecimal consumoMaio;
        private BigDecimal consumoJunho;
        private BigDecimal consumoJulho;
        private BigDecimal consumoAgosto;
        private BigDecimal consumoSetembro;
        private BigDecimal consumoOutubro;
        private BigDecimal consumoNovembro;
        private BigDecimal consumoDezembro;
        private BigDecimal consumoTotal;

        // Valores mensais de geração própria (MWh)
        private BigDecimal geracaoJaneiro;
        private BigDecimal geracaoFevereiro;
        private BigDecimal geracaoMarco;
        private BigDecimal geracaoAbril;
        private BigDecimal geracaoMaio;
        private BigDecimal geracaoJunho;
        private BigDecimal geracaoJulho;
        private BigDecimal geracaoAgosto;
        private BigDecimal geracaoSetembro;
        private BigDecimal geracaoOutubro;
        private BigDecimal geracaoNovembro;
        private BigDecimal geracaoDezembro;
        private BigDecimal geracaoTotal;

        // Valores mensais de perda (MWh) = Consumo × %Perda
        private BigDecimal perdaJaneiro;
        private BigDecimal perdaFevereiro;
        private BigDecimal perdaMarco;
        private BigDecimal perdaAbril;
        private BigDecimal perdaMaio;
        private BigDecimal perdaJunho;
        private BigDecimal perdaJulho;
        private BigDecimal perdaAgosto;
        private BigDecimal perdaSetembro;
        private BigDecimal perdaOutubro;
        private BigDecimal perdaNovembro;
        private BigDecimal perdaDezembro;
        private BigDecimal perdaTotal;

        // Necessidade total = Consumo + Perdas
        private BigDecimal necessidadeTotalJaneiro;
        private BigDecimal necessidadeTotalFevereiro;
        private BigDecimal necessidadeTotalMarco;
        private BigDecimal necessidadeTotalAbril;
        private BigDecimal necessidadeTotalMaio;
        private BigDecimal necessidadeTotalJunho;
        private BigDecimal necessidadeTotalJulho;
        private BigDecimal necessidadeTotalAgosto;
        private BigDecimal necessidadeTotalSetembro;
        private BigDecimal necessidadeTotalOutubro;
        private BigDecimal necessidadeTotalNovembro;
        private BigDecimal necessidadeTotalDezembro;
        private BigDecimal necessidadeTotal;

        // Necessidade de compra = MAX(Necessidade - Geração, 0)
        private BigDecimal necessidadeCompraJaneiro;
        private BigDecimal necessidadeCompraFevereiro;
        private BigDecimal necessidadeCompraMarco;
        private BigDecimal necessidadeCompraAbril;
        private BigDecimal necessidadeCompraMaio;
        private BigDecimal necessidadeCompraJunho;
        private BigDecimal necessidadeCompraJulho;
        private BigDecimal necessidadeCompraAgosto;
        private BigDecimal necessidadeCompraSetembro;
        private BigDecimal necessidadeCompraOutubro;
        private BigDecimal necessidadeCompraNovembro;
        private BigDecimal necessidadeCompraDezembro;
        private BigDecimal necessidadeCompraTotal;

        // Percentual de participação no consumo total
        private BigDecimal percentualConsumo;
    }
}
