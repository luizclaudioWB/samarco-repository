package br.com.wisebyte.samarco.model.planejamento.tarifa;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@SuperBuilder
public class TarifaDistribuidora extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false )
    private TarifaPlanejamento planejamento;

    @ManyToOne( fetch = LAZY )
    @JoinColumn( nullable = false )
    private Distribuidora distribuidora;

    @Column( nullable = false )
    private LocalDate periodoInicial;

    @Column( nullable = false )
    private LocalDate periodoFinal;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal valorPonta;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal valorForaPonta;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal valorEncargos;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal valorEncargosAutoProducao;

    @Column( precision = 20, scale = 10, nullable = false )
    private BigDecimal percentualPisCofins;

    @Column( nullable = false )
    private boolean sobrescreverICMS;

    @Column( precision = 20, scale = 10 )
    private BigDecimal percentualICMS;

    @Column( nullable = false )
    private Integer qtdeDeHorasPonta;

}
