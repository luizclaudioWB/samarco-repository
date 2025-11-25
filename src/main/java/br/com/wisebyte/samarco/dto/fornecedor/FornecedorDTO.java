package br.com.wisebyte.samarco.dto.fornecedor;

import br.com.wisebyte.samarco.model.estado.Estado;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FornecedorDTO {

    private Long id;

    private String nome;

    private String cnpj;

    private LocalDate inicioDatabase;

    private BigDecimal precoBase;

    private Estado estado;
}
