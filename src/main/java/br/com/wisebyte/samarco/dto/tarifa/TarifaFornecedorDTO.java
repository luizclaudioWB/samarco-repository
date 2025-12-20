package br.com.wisebyte.samarco.dto.tarifa;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.eclipse.microprofile.graphql.Name;

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

    /**
     * IPCA Total = IPCA Realizada + IPCA Projetado
     * Fórmula da planilha: =D12+E12
     */
    @Name("ipcaTotal")
    public BigDecimal getIpcaTotal() {
        if (ipcaRealizada == null || ipcaProjetado == null) {
            return BigDecimal.ZERO;
        }
        return ipcaRealizada.add(ipcaProjetado);
    }

    /**
     * Preço atualizado = Preço Base × (1 + IPCA Total)
     * Fórmula da planilha: =(C12)+(C12*F12) ou simplificando: =C12*(1+F12)
     */
    @Name("preco")
    public BigDecimal getPreco() {
        if (precoBase == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal ipcaTotal = getIpcaTotal();
        return precoBase.multiply(BigDecimal.ONE.add(ipcaTotal))
            .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Valor do Montante = Montante × Preço
     * Fórmula da planilha: =H12*G12
     */
    @Name("valorMontante")
    public BigDecimal getValorMontante() {
        if (montante == null) {
            return BigDecimal.ZERO;
        }
        return montante.multiply(getPreco())
            .setScale(2, RoundingMode.HALF_UP);
    }
}
