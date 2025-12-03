package br.com.wisebyte.samarco.dto.tarifa;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarifaFornecedorDTO {

    private Long id;

    private Long tarifaPlanejamentoId;

    private Long fornecedorId;

    private BigDecimal ipcaRealizada;

    private BigDecimal ipcaProjetado;

    private BigDecimal montante;
}
