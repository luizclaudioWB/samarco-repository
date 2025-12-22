package br.com.wisebyte.samarco.dto.consumo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoConsumoEspecificoDTO {

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

    public BigDecimal getMediaAnual() {
        var valores = Stream.of( valorJaneiro, valorFevereiro, valorMarco, valorAbril,
                        valorMaio, valorJunho, valorJulho, valorAgosto,
                        valorSetembro, valorOutubro, valorNovembro, valorDezembro )
                .filter( Objects::nonNull ).toList();
        if (valores.isEmpty()) return BigDecimal.ZERO;
        return valores.stream().reduce( BigDecimal.ZERO, BigDecimal::add )
                .divide( BigDecimal.valueOf(valores.size()), java.math.RoundingMode.HALF_UP );
    }
}
