package br.com.wisebyte.samarco.dto.area;

import br.com.wisebyte.samarco.model.area.TipoArea;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaDTO {

    private Long id;

    private Long unidadeId;

    private String usuarioId;

    private TipoArea tipoArea;

    private String nomeArea;

    private boolean ativo;
}
