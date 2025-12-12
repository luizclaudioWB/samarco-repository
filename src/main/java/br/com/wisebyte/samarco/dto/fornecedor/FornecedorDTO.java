package br.com.wisebyte.samarco.dto.fornecedor;

import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String nome;

    @NotNull
    private String cnpj;

    @NotNull
    private LocalDate inicioDatabase;

    @NotNull
    private BigDecimal precoBase;

    @NotNull
    private Estado estado;
}
