package br.com.wisebyte.samarco.model.unidade;

import br.com.wisebyte.samarco.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
public class Unidade extends BaseModel {

    private String nomeUnidade;

    private Boolean unidadeGeradora;

    @OneToOne
    private Unidade unidadeRecebedoraCreditosDeInjecao;

    private Boolean conectadaRedeBasica;
}
