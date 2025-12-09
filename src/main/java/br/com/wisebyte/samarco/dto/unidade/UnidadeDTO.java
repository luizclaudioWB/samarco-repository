package br.com.wisebyte.samarco.dto.unidade;

import br.com.wisebyte.samarco.model.estado.Estado;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnidadeDTO {

    private Long id;

    private String nome;

    private Boolean geraEnergia;

    private Long unidadeCedenteCreditosDeInjecao;

    private Boolean conectadaRedeBasica;

    private Estado estado;
}
