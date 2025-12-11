package br.com.wisebyte.samarco.dto.tarifa;

import br.com.wisebyte.samarco.dto.fornecedor.FornecedorDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MontanteTarifaFornecedorDTO {

    FornecedorDTO fornecedor;

    TarifaFornecedorDTO tarifaFornecedor;

    BigDecimal preco;

    BigDecimal ipcaTotal;

    BigDecimal valorMontante;

}
