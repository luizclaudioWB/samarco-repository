package br.com.wisebyte.samarco.model.unidade;

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
public class Unidade extends BaseModel {

    @Column( nullable = false )
    private String nomeUnidade;

    @Column( nullable = false )
    private Boolean unidadeGeradora;

    @OneToOne
    private Unidade unidadeRecebedoraCreditosDeInjecao;

    @Column( nullable = false )
    private Boolean conectadaRedeBasica;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private Estado estado;
}
