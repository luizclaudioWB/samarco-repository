package br.com.wisebyte.samarco.model.planejamento;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table
public class Revisao extends BaseModel {

    @Column( nullable = false )
    private Integer numeroRevisao;

    @JoinColumn( nullable = false)
    @ManyToOne(fetch = LAZY)
    private Usuario usuario;

    @JoinColumn( nullable = false)
    @ManyToOne(fetch = LAZY)
    private Planejamento planejamento;

    @Column( nullable = false )
    private String descricao;

    @Column( nullable = false )
    private boolean oficial;

    private boolean finished;

}
