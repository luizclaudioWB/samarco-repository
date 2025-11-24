package br.com.wisebyte.samarco.model.planejamento.tarifa;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.fornecedor.Fornecedor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@SuperBuilder
public class TarifaFornecedor extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false )
    private TarifaPlanejamento planejamento;

    @ManyToOne( fetch = LAZY )
    @JoinColumn( nullable = false )
    private Fornecedor fornecedor;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal ipcaRealizada;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal ipcaProjetado;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal montante;
}
