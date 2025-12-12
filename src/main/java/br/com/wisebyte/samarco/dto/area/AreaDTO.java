package br.com.wisebyte.samarco.dto.area;

import br.com.wisebyte.samarco.model.area.TipoArea;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaDTO {

    private Long id;

    @NotNull
    private Long unidadeId;

    @NotNull
    private String usuarioId;

    @NotNull
    private TipoArea tipoArea;

    @NotNull
    private String nomeArea;

    private boolean ativo;
}
