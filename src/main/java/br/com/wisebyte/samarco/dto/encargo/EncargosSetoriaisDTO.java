package br.com.wisebyte.samarco.dto.encargo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncargosSetoriaisDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    // EER - Energia de Reserva (R$/MWh)
    private BigDecimal eerJaneiro;
    private BigDecimal eerFevereiro;
    private BigDecimal eerMarco;
    private BigDecimal eerAbril;
    private BigDecimal eerMaio;
    private BigDecimal eerJunho;
    private BigDecimal eerJulho;
    private BigDecimal eerAgosto;
    private BigDecimal eerSetembro;
    private BigDecimal eerOutubro;
    private BigDecimal eerNovembro;
    private BigDecimal eerDezembro;

    // ERCAP - Reserva de Capacidade (R$/MWh)
    private BigDecimal ercapJaneiro;
    private BigDecimal ercapFevereiro;
    private BigDecimal ercapMarco;
    private BigDecimal ercapAbril;
    private BigDecimal ercapMaio;
    private BigDecimal ercapJunho;
    private BigDecimal ercapJulho;
    private BigDecimal ercapAgosto;
    private BigDecimal ercapSetembro;
    private BigDecimal ercapOutubro;
    private BigDecimal ercapNovembro;
    private BigDecimal ercapDezembro;

    // ESS - Encargos de Serviços do Sistema (R$/MWh)
    private BigDecimal essJaneiro;
    private BigDecimal essFevereiro;
    private BigDecimal essMarco;
    private BigDecimal essAbril;
    private BigDecimal essMaio;
    private BigDecimal essJunho;
    private BigDecimal essJulho;
    private BigDecimal essAgosto;
    private BigDecimal essSetembro;
    private BigDecimal essOutubro;
    private BigDecimal essNovembro;
    private BigDecimal essDezembro;
}
