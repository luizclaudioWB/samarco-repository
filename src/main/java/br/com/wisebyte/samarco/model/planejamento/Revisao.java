package br.com.wisebyte.samarco.model.planejamento;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Revisao extends BaseModel {

    private Short numeroRevisao;

    @ManyToOne(fetch = LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = LAZY)
    private Planejamento planejamento;

    private String descricao;

}
