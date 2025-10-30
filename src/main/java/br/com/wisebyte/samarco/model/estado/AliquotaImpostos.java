package br.com.wisebyte.samarco.model.estado;

import br.com.wisebyte.samarco.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@SuperBuilder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ano", "estado" }) })
public class AliquotaImpostos extends BaseModel {

    @Column( nullable = false)
    private Short ano;

    @Column( nullable = false)
    private Estado estado;

    @Column( nullable = false)
    private Double percentualPis;

    @Column( nullable = false)
    private Double percentualCofins;

    @Column( nullable = false)
    private Double percentualIcms;

    @Column( nullable = false)
    private Double percentualIpca;
}
