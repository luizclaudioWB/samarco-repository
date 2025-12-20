package br.com.wisebyte.samarco.dto.consumo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para resultado do cálculo de Consumo de Área.
 *
 * Fórmula: ConsumoArea = Producao × ConsumoEspecifico
 * Onde: Producao = PlanejamentoProducao × 1000
 *
 * Exceção: Mineração usa soma das produções de Beneficiamento.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoAreaResultDTO {

    private Long areaId;
    private String nomeArea;
    private String centroCusto;
    private String tipoArea;
    private String estado;

    // Valores mensais em kWh
    private BigDecimal valorJaneiro;
    private BigDecimal valorFevereiro;
    private BigDecimal valorMarco;
    private BigDecimal valorAbril;
    private BigDecimal valorMaio;
    private BigDecimal valorJunho;
    private BigDecimal valorJulho;
    private BigDecimal valorAgosto;
    private BigDecimal valorSetembro;
    private BigDecimal valorOutubro;
    private BigDecimal valorNovembro;
    private BigDecimal valorDezembro;

    // Valores auxiliares para debug/transparência
    private BigDecimal producaoUtilizada;      // tms (pode ser da própria área ou soma de beneficiamentos)
    private BigDecimal consumoEspecificoUsado; // kWh/tms

    /**
     * Total anual em kWh
     */
    public BigDecimal getTotalAnualKwh() {
        BigDecimal total = BigDecimal.ZERO;
        if (valorJaneiro != null) total = total.add(valorJaneiro);
        if (valorFevereiro != null) total = total.add(valorFevereiro);
        if (valorMarco != null) total = total.add(valorMarco);
        if (valorAbril != null) total = total.add(valorAbril);
        if (valorMaio != null) total = total.add(valorMaio);
        if (valorJunho != null) total = total.add(valorJunho);
        if (valorJulho != null) total = total.add(valorJulho);
        if (valorAgosto != null) total = total.add(valorAgosto);
        if (valorSetembro != null) total = total.add(valorSetembro);
        if (valorOutubro != null) total = total.add(valorOutubro);
        if (valorNovembro != null) total = total.add(valorNovembro);
        if (valorDezembro != null) total = total.add(valorDezembro);
        return total;
    }

    /**
     * Total anual em MWh (kWh / 1000)
     */
    public BigDecimal getTotalAnualMwh() {
        return getTotalAnualKwh().divide(new BigDecimal("1000"), 2, java.math.RoundingMode.HALF_UP);
    }
}
