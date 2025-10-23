package br.com.wisebyte.samarco.model.tarifa;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @ManyToOne(fetch = LAZY)
    private Revisao revisao;

    private LocalDate periodoInicial;

    private LocalDate periodoFinal;

    private Double valorPonta;

    private Double valorForaPonta;

    private Double valorEncargos;

    private Double valorEncargosAutoProducao;

    private Double percentualPisCofins;

}
