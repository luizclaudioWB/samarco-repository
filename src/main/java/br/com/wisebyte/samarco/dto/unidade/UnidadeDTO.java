package br.com.wisebyte.samarco.dto.unidade;

import br.com.wisebyte.samarco.model.estado.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnidadeDTO {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean geraEnergia;

    private Long unidadeCedenteCreditosDeInjecao;

    @NotNull
    private Boolean conectadaRedeBasica;

    @NotNull
    private Estado estado;

    private Long distribuidoraId;
}
