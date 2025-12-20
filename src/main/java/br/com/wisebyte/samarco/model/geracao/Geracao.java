package br.com.wisebyte.samarco.model.geracao;

import br.com.wisebyte.samarco.model.BaseModel;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Representa a geração de energia própria por unidade geradora (MWh).
 *
 * Somente unidades com unidadeGeradora=true podem ter registros de geração.
 * Exemplos: UHE Guilman Amorim, PCH Muniz Freire.
 *
 * Chave única: (revisao + unidade)
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_geracao_revisao_unidade",
            columnNames = {"revisao_id", "unidade_id"}
        )
    }
)
public class Geracao extends BaseModel {

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Revisao revisao;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Unidade unidade;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorJaneiro;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorFevereiro;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorMarco;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorAbril;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorMaio;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorJunho;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorJulho;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorAgosto;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorSetembro;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorOutubro;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorNovembro;

    @Min(value = 0)
    @Column(precision = 20, scale = 10)
    private BigDecimal valorDezembro;
}
