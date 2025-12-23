package br.com.wisebyte.samarco.model.encargo;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Encargos setoriais do sistema elétrico.
 * Baseado na Sheet 12 da planilha - "Encargos ESS EER".
 *
 * Valores em R$/MWh que incidem sobre energia COMPRADA.
 * - EER: Energia de Reserva
 * - ERCAP: Reserva de Capacidade
 * - ESS: Encargos de Serviços do Sistema
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"revisao_id"})})
@SuperBuilder
public class EncargosSetoriais extends BaseModel {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Revisao revisao;

    // EER - Energia de Reserva (R$/MWh)
    @Column(precision = 20, scale = 6)
    private BigDecimal eerJaneiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerFevereiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerMarco;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerAbril;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerMaio;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerJunho;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerJulho;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerAgosto;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerSetembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerOutubro;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerNovembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal eerDezembro;

    // ERCAP - Reserva de Capacidade (R$/MWh)
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapJaneiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapFevereiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapMarco;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapAbril;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapMaio;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapJunho;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapJulho;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapAgosto;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapSetembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapOutubro;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapNovembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal ercapDezembro;

    // ESS - Encargos de Serviços do Sistema (R$/MWh)
    @Column(precision = 20, scale = 6)
    private BigDecimal essJaneiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal essFevereiro;
    @Column(precision = 20, scale = 6)
    private BigDecimal essMarco;
    @Column(precision = 20, scale = 6)
    private BigDecimal essAbril;
    @Column(precision = 20, scale = 6)
    private BigDecimal essMaio;
    @Column(precision = 20, scale = 6)
    private BigDecimal essJunho;
    @Column(precision = 20, scale = 6)
    private BigDecimal essJulho;
    @Column(precision = 20, scale = 6)
    private BigDecimal essAgosto;
    @Column(precision = 20, scale = 6)
    private BigDecimal essSetembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal essOutubro;
    @Column(precision = 20, scale = 6)
    private BigDecimal essNovembro;
    @Column(precision = 20, scale = 6)
    private BigDecimal essDezembro;
}
