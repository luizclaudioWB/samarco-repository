package br.com.wisebyte.samarco.dto.demanda;

import br.com.wisebyte.samarco.model.demanda.TipoHorario;
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
public class DemandaDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    @NotNull
    private Long unidadeId;

    @NotNull
    private TipoHorario horario;

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
     * Calcula a média anual dos valores mensais preenchidos.
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
            2,
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
