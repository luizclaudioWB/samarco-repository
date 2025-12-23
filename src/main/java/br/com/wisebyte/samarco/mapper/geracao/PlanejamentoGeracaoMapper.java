package br.com.wisebyte.samarco.mapper.geracao;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.geracao.PlanejamentoGeracaoDTO;
import br.com.wisebyte.samarco.model.geracao.PlanejamentoGeracao;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanejamentoGeracaoMapper implements EntityMapper<PlanejamentoGeracao, PlanejamentoGeracaoDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Override
    public PlanejamentoGeracao toEntity(PlanejamentoGeracaoDTO dto) {
        return PlanejamentoGeracao.builder()
                .id(dto.getId())
                .revisao(dto.getRevisaoId() != null
                        ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null) : null)
                .unidade(dto.getUnidadeId() != null
                        ? unidadeRepository.findById(dto.getUnidadeId()).orElse(null) : null)
                .valorJaneiro(dto.getValorJaneiro())
                .valorFevereiro(dto.getValorFevereiro())
                .valorMarco(dto.getValorMarco())
                .valorAbril(dto.getValorAbril())
                .valorMaio(dto.getValorMaio())
                .valorJunho(dto.getValorJunho())
                .valorJulho(dto.getValorJulho())
                .valorAgosto(dto.getValorAgosto())
                .valorSetembro(dto.getValorSetembro())
                .valorOutubro(dto.getValorOutubro())
                .valorNovembro(dto.getValorNovembro())
                .valorDezembro(dto.getValorDezembro())
                .build();
    }

    @Override
    public PlanejamentoGeracaoDTO toDTO(PlanejamentoGeracao entity) {
        return PlanejamentoGeracaoDTO.builder()
                .id(entity.getId())
                .revisaoId(entity.getRevisao() != null ? entity.getRevisao().getId() : null)
                .unidadeId(entity.getUnidade() != null ? entity.getUnidade().getId() : null)
                .valorJaneiro(entity.getValorJaneiro())
                .valorFevereiro(entity.getValorFevereiro())
                .valorMarco(entity.getValorMarco())
                .valorAbril(entity.getValorAbril())
                .valorMaio(entity.getValorMaio())
                .valorJunho(entity.getValorJunho())
                .valorJulho(entity.getValorJulho())
                .valorAgosto(entity.getValorAgosto())
                .valorSetembro(entity.getValorSetembro())
                .valorOutubro(entity.getValorOutubro())
                .valorNovembro(entity.getValorNovembro())
                .valorDezembro(entity.getValorDezembro())
                .build();
    }
}
