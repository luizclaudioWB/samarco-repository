package br.com.wisebyte.samarco.model.distribuidora;

import br.com.wisebyte.samarco.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Integer qtdeDeHorasPonta;
}
