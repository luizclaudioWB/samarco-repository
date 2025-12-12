package br.com.wisebyte.samarco.dto.producao;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducaoConfigDTO {

    private Long id;

    @NotNull
    private Long revisaoId;

    private Integer multiplicador;

    @Builder.Default
    private Set<Long> areaIds = new HashSet<>();
}
