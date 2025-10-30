package br.com.wisebyte.samarco.model.producao;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.area.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ano" }) })
public class ProducaoConfig extends BaseModel {

    @Column( nullable = false)
    private Integer ano;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "producao_config_areas",
            joinColumns = @JoinColumn(name = "producao_config_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<Area> areas = new HashSet<>();
}
