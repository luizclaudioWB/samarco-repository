package br.com.wisebyte.samarco.dto.prducao;

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
public class PlanejamentoProducaoDTO {

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

    public BigDecimal getTotalAnual() {
        return Stream.of( valorJaneiro, valorFevereiro, valorMarco, valorAbril,
                        valorMaio, valorJunho, valorJulho, valorAgosto,
                        valorSetembro, valorOutubro, valorNovembro, valorDezembro )
                .filter( Objects::nonNull ).reduce( BigDecimal.ZERO, BigDecimal::add );
    }
}

