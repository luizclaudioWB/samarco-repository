package br.com.wisebyte.samarco.dto.tarifa;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    // Preço base do fornecedor (vem do Fornecedor.precoBase)
    private BigDecimal precoBase;

    @NotNull
    private BigDecimal ipcaRealizada;

    @NotNull
    private BigDecimal ipcaProjetado;

    @NotNull
    private BigDecimal montante;

    // Campos calculados (preenchidos no mapper/builder)
    private BigDecimal ipcaTotal;
    private BigDecimal preco;
    private BigDecimal valorMontante;

    /**
     * Calcula e preenche os campos calculados.
     * Deve ser chamado após setar os valores base.
     */
    public void calcularCampos() {
        // IPCA Total = IPCA Realizada + IPCA Projetado
        if (ipcaRealizada != null && ipcaProjetado != null) {
            this.ipcaTotal = ipcaRealizada.add(ipcaProjetado);
        } else {
            this.ipcaTotal = BigDecimal.ZERO;
        }

        // Preço = Preço Base × (1 + IPCA Total)
        if (precoBase != null) {
            this.preco = precoBase.multiply(BigDecimal.ONE.add(this.ipcaTotal))
                .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.preco = BigDecimal.ZERO;
        }

        // Valor Montante = Montante × Preço
        if (montante != null && this.preco != null) {
            this.valorMontante = montante.multiply(this.preco)
                .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.valorMontante = BigDecimal.ZERO;
        }
    }
}
