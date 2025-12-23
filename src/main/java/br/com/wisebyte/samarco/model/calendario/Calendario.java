package br.com.wisebyte.samarco.model.calendario;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Calendário com dias não úteis por mês.
 * Baseado na Sheet 9 da planilha - seção "Calendário".
 *
 * Dias úteis = Dias no mês - Dias não úteis
 * Horas Ponta = Dias úteis × qtdeHorasPontaDia
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"revisao_id"})})
@SuperBuilder
public class Calendario extends BaseModel {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Revisao revisao;

    /** Quantidade de horas de ponta por dia (padrão: 3) */
    @Column(nullable = false)
    private Integer qtdeHorasPontaDia;

    // Dias NÃO úteis por mês (feriados + finais de semana)
    @Column(nullable = false)
    private Integer diasNaoUteisJaneiro;

    @Column(nullable = false)
    private Integer diasNaoUteisFevereiro;

    @Column(nullable = false)
    private Integer diasNaoUteisMarco;

    @Column(nullable = false)
    private Integer diasNaoUteisAbril;

    @Column(nullable = false)
    private Integer diasNaoUteisMaio;

    @Column(nullable = false)
    private Integer diasNaoUteisJunho;

    @Column(nullable = false)
    private Integer diasNaoUteisJulho;

    @Column(nullable = false)
    private Integer diasNaoUteisAgosto;

    @Column(nullable = false)
    private Integer diasNaoUteisSetembro;

    @Column(nullable = false)
    private Integer diasNaoUteisOutubro;

    @Column(nullable = false)
    private Integer diasNaoUteisNovembro;

    @Column(nullable = false)
    private Integer diasNaoUteisDezembro;
}
