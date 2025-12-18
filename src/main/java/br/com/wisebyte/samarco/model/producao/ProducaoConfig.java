package br.com.wisebyte.samarco.model.producao;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table( uniqueConstraints = {@UniqueConstraint( columnNames = {"revisao_id"} )} )
public class ProducaoConfig extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false )
    private Revisao revisao;

    private Integer multiplicador;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable(
            name = "producao_config_areas",
            joinColumns = @JoinColumn(name = "producao_config_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    @Builder.Default
    private Set<Area> areas = new HashSet<>();
}
