package br.com.wisebyte.samarco.dto.consumo;

import br.com.wisebyte.samarco.dto.area.AreaIdDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoAreaResultDTO {

    private AreaIdDTO area;

    private String nomeArea;

    private String tipoArea;

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

    public BigDecimal getTotalAnual() {
        return Stream.of( consumoJaneiro, consumoFevereiro, consumoMarco, consumoAbril,
                        consumoMaio, consumoJunho, consumoJulho, consumoAgosto,
                        consumoSetembro, consumoOutubro, consumoNovembro, consumoDezembro )
                .filter( Objects::nonNull ).reduce( BigDecimal.ZERO, BigDecimal::add );
    }
}
