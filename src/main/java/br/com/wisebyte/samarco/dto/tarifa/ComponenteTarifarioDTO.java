package br.com.wisebyte.samarco.dto.tarifa;

import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponenteTarifarioDTO {

    DistribuidoraDTO distribuidora;

    LocalDate inicioVigencia;

    LocalDate fimVigencia;

    TipoComponenteTarifarioDTO componente;

    BigDecimal valorBruto;

    BigDecimal valorICMS;

    BigDecimal valorPis;

    BigDecimal valorCofins;

    BigDecimal liquidoSAP;
}
