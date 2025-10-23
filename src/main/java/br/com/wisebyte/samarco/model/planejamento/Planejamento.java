package br.com.wisebyte.samarco.model.planejamento;

import br.com.wisebyte.samarco.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class Planejamento extends BaseModel {

    @Column( unique = true)
    private Short ano;

    @Lob
    @Column( nullable = false)
    private String descricao;

    @Lob
    private String mensagem;

    @Column( nullable = false)
    private Boolean corrente;
}
