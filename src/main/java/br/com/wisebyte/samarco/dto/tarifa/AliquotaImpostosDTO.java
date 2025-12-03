package br.com.wisebyte.samarco.dto.tarifa;

import br.com.wisebyte.samarco.model.estado.Estado;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliquotaImpostosDTO {

    private Long id;

    private Long tarifaPlanejamentoId;

    private Integer ano;

    private Estado estado;

    private BigDecimal percentualPis;

    private BigDecimal percentualCofins;

    private BigDecimal percentualIcms;

    private BigDecimal percentualIpca;

    public BigDecimal getTotalImpostos() {
        return Stream.of(percentualPis, percentualCofins, percentualIcms)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
