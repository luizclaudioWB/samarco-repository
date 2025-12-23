package br.com.wisebyte.samarco.dto.custo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Resultado do Resumo Geral de Custos.
 * Baseado na Sheet 16 da planilha - "Resumo GERAL".
 *
 * Consolida todos os custos de energia elétrica.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumoGeralResultDTO {

    private Long revisaoId;

    // Custos Variáveis
    private CustosMensaisDTO consumoEnergia;      // 50610002
    private CustosMensaisDTO usoRede;             // 50610003
    private CustosMensaisDTO encargos;            // 50610006
    private CustosMensaisDTO eerErcap;            // 50610007 - EER/ERCAP
    private CustosMensaisDTO ess;                 // 50610008 - ESS

    // Totais
    private CustosMensaisDTO totalVariavel;
    private CustosMensaisDTO totalGeral;

    // Produção (para cálculo de custo específico)
    private BigDecimal producaoTotalTms;

    // Custo Específico = Total / Produção (R$/tms)
    private BigDecimal custoEspecificoTotal;

    // Resumo por tipo
    private BigDecimal totalConsumoEnergiaAnual;
    private BigDecimal totalUsoRedeAnual;
    private BigDecimal totalEncargosAnual;
    private BigDecimal totalEerErcapAnual;
    private BigDecimal totalEssAnual;
    private BigDecimal totalVariavelAnual;
    private BigDecimal totalGeralAnual;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustosMensaisDTO {
        private BigDecimal janeiro;
        private BigDecimal fevereiro;
        private BigDecimal marco;
        private BigDecimal abril;
        private BigDecimal maio;
        private BigDecimal junho;
        private BigDecimal julho;
        private BigDecimal agosto;
        private BigDecimal setembro;
        private BigDecimal outubro;
        private BigDecimal novembro;
        private BigDecimal dezembro;
        private BigDecimal total;
    }
}
