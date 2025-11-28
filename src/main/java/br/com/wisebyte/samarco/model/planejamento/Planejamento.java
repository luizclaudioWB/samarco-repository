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
import org.hibernate.annotations.Formula;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
    @jakarta.persistence.UniqueConstraint(name = "uk_planejamento_ano", columnNames = {"ano"})
})
@SuperBuilder
public class Planejamento extends BaseModel {

    @Column(nullable = false)
    private Integer ano;

    @Lob
    @Column( nullable = false)
    private String descricao;

    @Lob
    private String mensagem;

    @Formula("YEAR(CURRENT_DATE) = ano")
    @Column( insertable = false, updatable = false )
    private Boolean corrente;
}
