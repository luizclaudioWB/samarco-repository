package br.com.wisebyte.samarco.model.area;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.model.unidade.Unidade;
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
@Table
@SuperBuilder
public class Area extends BaseModel {

    @JoinColumn( nullable = false)
    @ManyToOne(fetch = LAZY)
    private Unidade unidade;

    @JoinColumn( nullable = false)
    @ManyToOne(fetch = LAZY)
    private Usuario usuario;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoArea tipoArea;

    @Column( nullable = false)
    private String nomeArea;

    @Column( nullable = false)
    private boolean ativo;

}
