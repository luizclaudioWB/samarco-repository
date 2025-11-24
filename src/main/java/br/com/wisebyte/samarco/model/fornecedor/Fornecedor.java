package br.com.wisebyte.samarco.model.fornecedor;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Fornecedor extends BaseModel {

    @NotNull
    @Column( nullable = false)
    private String nome;

    @NotNull
    @Column( nullable = false)
    private String cnpj;

    @NotNull
    @Column( nullable = false )
    private LocalDate inicioDatabase;

    @NotNull
    @Column( nullable = false, precision = 20, scale = 10 )
    private BigDecimal precoBase;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private Estado estado;
}
