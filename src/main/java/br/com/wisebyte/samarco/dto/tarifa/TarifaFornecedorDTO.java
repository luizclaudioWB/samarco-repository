package br.com.wisebyte.samarco.dto.tarifa;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarifaFornecedorDTO {

    private Long id;

    @NotNull
    private Long tarifaPlanejamentoId;

    @NotNull
    private Long fornecedorId;

    @NotNull
    private BigDecimal ipcaRealizada;

    @NotNull
    private BigDecimal ipcaProjetado;

    @NotNull
    private BigDecimal montante;
}
