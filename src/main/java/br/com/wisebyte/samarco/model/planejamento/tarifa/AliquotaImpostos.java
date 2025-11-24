package br.com.wisebyte.samarco.model.planejamento.tarifa;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table( uniqueConstraints = {@UniqueConstraint( columnNames = {"ano", "estado", "planejamento_id"} )} )
public class AliquotaImpostos extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false )
    private TarifaPlanejamento planejamento;

    @Column( nullable = false )
    private Integer ano;

    @Column( nullable = false )
    @Enumerated( EnumType.STRING )
    private Estado estado;

    @Column( nullable = false )
    private BigDecimal percentualPis;

    @Column( nullable = false )
    private BigDecimal percentualCofins;

    @Column( nullable = false )
    private BigDecimal percentualIcms;

    @Column( nullable = false )
    private BigDecimal percentualIpca;
}
