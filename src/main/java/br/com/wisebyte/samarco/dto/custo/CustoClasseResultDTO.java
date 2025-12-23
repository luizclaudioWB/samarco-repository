package br.com.wisebyte.samarco.dto.custo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Resultado do cálculo de Custo por Classe.
 * Baseado na Sheet 14 da planilha - "Distrib de Classe de Custo".
 *
 * Classes:
 * - 50610002: Consumo de Energia (Energia Comprada × PMIX)
 * - 50610003: Uso da Rede (Demanda × Tarifa)
 * - 50610006: Encargos (Consumo × Encargo - Desconto Auto-Produção)
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustoClasseResultDTO {

    private Long revisaoId;

    // Custos de Minas Gerais
    private CustoClasseEstadoDTO minasGerais;

    // Custos de Espírito Santo
    private CustoClasseEstadoDTO espiritoSanto;

    // Totais Samarco
    private CustoClasseTotalDTO totalSamarco;

    // PMIX calculado (preço médio ponderado dos fornecedores)
    private BigDecimal pmix;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustoClasseEstadoDTO {

        // 50610002 - Consumo de Energia (R$) = Energia Comprada × PMIX
        private BigDecimal consumoEnergiaJaneiro;
        private BigDecimal consumoEnergiaFevereiro;
        private BigDecimal consumoEnergiaMarco;
        private BigDecimal consumoEnergiaAbril;
        private BigDecimal consumoEnergiaMaio;
        private BigDecimal consumoEnergiaJunho;
        private BigDecimal consumoEnergiaJulho;
        private BigDecimal consumoEnergiaAgosto;
        private BigDecimal consumoEnergiaSetembro;
        private BigDecimal consumoEnergiaOutubro;
        private BigDecimal consumoEnergiaNovembro;
        private BigDecimal consumoEnergiaDezembro;
        private BigDecimal consumoEnergiaTotal;

        // 50610003 - Uso da Rede (R$) = Demanda × Tarifa
        private BigDecimal usoRedeJaneiro;
        private BigDecimal usoRedeFevereiro;
        private BigDecimal usoRedeMarco;
        private BigDecimal usoRedeAbril;
        private BigDecimal usoRedeMaio;
        private BigDecimal usoRedeJunho;
        private BigDecimal usoRedeJulho;
        private BigDecimal usoRedeAgosto;
        private BigDecimal usoRedeSetembro;
        private BigDecimal usoRedeOutubro;
        private BigDecimal usoRedeNovembro;
        private BigDecimal usoRedeDezembro;
        private BigDecimal usoRedeTotal;

        // 50610006 - Encargos (R$) = Consumo × Encargo - Desconto
        private BigDecimal encargosJaneiro;
        private BigDecimal encargosFevereiro;
        private BigDecimal encargosMarco;
        private BigDecimal encargosAbril;
        private BigDecimal encargosMaio;
        private BigDecimal encargosJunho;
        private BigDecimal encargosJulho;
        private BigDecimal encargosAgosto;
        private BigDecimal encargosSetembro;
        private BigDecimal encargosOutubro;
        private BigDecimal encargosNovembro;
        private BigDecimal encargosDezembro;
        private BigDecimal encargosTotal;

        // Desconto Auto-Produção (R$)
        private BigDecimal descontoAutoProducaoJaneiro;
        private BigDecimal descontoAutoProducaoFevereiro;
        private BigDecimal descontoAutoProducaoMarco;
        private BigDecimal descontoAutoProducaoAbril;
        private BigDecimal descontoAutoProducaoMaio;
        private BigDecimal descontoAutoProducaoJunho;
        private BigDecimal descontoAutoProducaoJulho;
        private BigDecimal descontoAutoProducaoAgosto;
        private BigDecimal descontoAutoProducaoSetembro;
        private BigDecimal descontoAutoProducaoOutubro;
        private BigDecimal descontoAutoProducaoNovembro;
        private BigDecimal descontoAutoProducaoDezembro;
        private BigDecimal descontoAutoProducaoTotal;

        // Total do estado
        private BigDecimal totalJaneiro;
        private BigDecimal totalFevereiro;
        private BigDecimal totalMarco;
        private BigDecimal totalAbril;
        private BigDecimal totalMaio;
        private BigDecimal totalJunho;
        private BigDecimal totalJulho;
        private BigDecimal totalAgosto;
        private BigDecimal totalSetembro;
        private BigDecimal totalOutubro;
        private BigDecimal totalNovembro;
        private BigDecimal totalDezembro;
        private BigDecimal totalGeral;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustoClasseTotalDTO {
        private BigDecimal consumoEnergiaTotal;  // 50610002
        private BigDecimal usoRedeTotal;         // 50610003
        private BigDecimal encargosTotal;        // 50610006
        private BigDecimal totalGeral;
    }
}
