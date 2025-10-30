package br.com.wisebyte.samarco.model.producao;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(
        uniqueConstraints = { 
                @UniqueConstraint(columnNames = { "ano" }) 
        }
)
public class PlanejamentoProducao extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false)
    private Revisao revisao;

    @NotNull
    @ManyToOne
    @JoinColumn( nullable = false)
    private Area area;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorJaneiro;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorFevereiro;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorMarco;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorAbril;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorMaio;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorJunho;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorJulho;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorAgosto;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorSetembro;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorOutubro;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorNovembro;

    @Min( value = 0 )
    @Column( precision = 20, scale = 10 )
    private BigDecimal valorDezembro;

}
