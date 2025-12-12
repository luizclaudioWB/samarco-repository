package br.com.wisebyte.samarco.dto.tarifa;

import br.com.wisebyte.samarco.model.estado.Estado;
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
public class AliquotaImpostosDTO {

    private Long id;

    @NotNull
    private Long tarifaPlanejamentoId;

    @NotNull
    private Integer ano;

    @NotNull
    private Estado estado;

    @NotNull
    private BigDecimal percentualPis;

    @NotNull
    private BigDecimal percentualCofins;

    @NotNull
    private BigDecimal percentualIcms;

    @NotNull
    private BigDecimal percentualIpca;

    public BigDecimal getTotalImpostos() {
        return Stream.of(percentualPis, percentualCofins, percentualIcms)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
