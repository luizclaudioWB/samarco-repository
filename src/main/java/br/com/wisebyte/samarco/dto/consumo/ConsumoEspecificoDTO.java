package br.com.wisebyte.samarco.dto.consumo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoEspecificoDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    @NotNull
    private Long areaId;

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

    /**
     * Calcula a média anual do consumo específico (kWh/tms).
     */
    public BigDecimal getMediaAnual() {
        List<BigDecimal> valores = Stream.of(
                valorJaneiro, valorFevereiro, valorMarco, valorAbril,
                valorMaio, valorJunho, valorJulho, valorAgosto,
                valorSetembro, valorOutubro, valorNovembro, valorDezembro
            )
            .filter(Objects::nonNull)
            .toList();

        if (valores.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal soma = valores.stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return soma.divide(
            BigDecimal.valueOf(valores.size()),
            4,
            RoundingMode.HALF_UP
        );
    }

    /**
     * Calcula o total anual (soma de todos os meses).
     */
    public BigDecimal getTotalAnual() {
        return Stream.of(
                valorJaneiro, valorFevereiro, valorMarco, valorAbril,
                valorMaio, valorJunho, valorJulho, valorAgosto,
                valorSetembro, valorOutubro, valorNovembro, valorDezembro
            )
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
