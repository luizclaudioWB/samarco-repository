package br.com.wisebyte.samarco.model.distribuidora;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.persistence.*;
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
@Table
@SuperBuilder
public class Distribuidora  extends BaseModel {

    @Column( nullable = false)
    private String nome;

    @Column( unique = true)
    private String cnpj;

    @Column( nullable = false)
    private String siglaAgente;

    @Column( nullable = false)
    @Enumerated( EnumType.STRING )
    private Estado estado;
}
