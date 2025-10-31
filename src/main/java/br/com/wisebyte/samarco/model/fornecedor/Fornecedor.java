package br.com.wisebyte.samarco.model.fornecedor;

import br.com.wisebyte.samarco.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
}
