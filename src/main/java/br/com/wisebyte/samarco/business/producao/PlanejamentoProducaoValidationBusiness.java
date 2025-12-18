package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Optional;


@ApplicationScoped
public class PlanejamentoProducaoValidationBusiness {

    @Inject
    PlanejamentoProducaoRepository planejamentoProducaoRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    AreaRepository areaRepository;

    @Inject
    ProducaoConfigRepository producaoConfigRepository;

    public boolean idIsNull(PlanejamentoProducaoDTO dto) {
        return dto.getId() == null;
    }

    public boolean exists(Long id) {
        return planejamentoProducaoRepository.findById(id).isPresent();
    }

    public boolean revisaoExists(Long revisaoId) {
        return revisaoId != null &&
                revisaoRepository.findById(revisaoId).isPresent();
    }

    public boolean areaExists(Long areaId) {
        return areaId != null &&
                areaRepository.findById(areaId).isPresent();
    }

    // Vetera aqui pensei e conclui que precisamos verificar se a combinacao revisao + area ja existe
    public boolean combinacaoRevisaoAreaUnica(PlanejamentoProducaoDTO dto) {
        Revisao revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElse(null);
        Area area = areaRepository.findById(dto.getAreaId())
                .orElse(null);

        if (revisao == null || area == null) {
            return true; // Vai falhar em outra validacao
        }

        Optional<PlanejamentoProducao> existente =
                planejamentoProducaoRepository.findByRevisaoAndArea(revisao, area);

        // Se nao existe, e unico
        if (existente.isEmpty()) return true;

        // Se existe, verifica se e o mesmo registro (update)
        return existente.get().getId().equals(dto.getId());
    }

    public boolean valoresMensaisValidos(PlanejamentoProducaoDTO dto) {
        return isPositiveOrNull(dto.getValorJaneiro()) &&
                isPositiveOrNull(dto.getValorFevereiro()) &&
                isPositiveOrNull(dto.getValorMarco()) &&
                isPositiveOrNull(dto.getValorAbril()) &&
                isPositiveOrNull(dto.getValorMaio()) &&
                isPositiveOrNull(dto.getValorJunho()) &&
                isPositiveOrNull(dto.getValorJulho()) &&
                isPositiveOrNull(dto.getValorAgosto()) &&
                isPositiveOrNull(dto.getValorSetembro()) &&
                isPositiveOrNull(dto.getValorOutubro()) &&
                isPositiveOrNull(dto.getValorNovembro()) &&
                isPositiveOrNull(dto.getValorDezembro());
    }

    private boolean isPositiveOrNull(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) >= 0;
    }

    // Verifica se a Revisao NAO esta finalizada, se está não mexemos
    public boolean revisaoNaoFinalizada(Long revisaoId) {
        return revisaoRepository.findById(revisaoId)
                .map(revisao -> !revisao.isFinished())
                .orElse(false);
    }

    // Verifica se a Area esta ativa
    public boolean areaAtiva(Long areaId) {
        return areaRepository.findById(areaId)
                .map(Area::isAtivo)
                .orElse(false);
    }

    // Verifica se a Area pertence ao ProducaoConfig da Revisao
    public boolean areaEstaNaProducaoConfig(Long revisaoId, Long areaId) {
        return producaoConfigRepository.countAreaInProducaoConfig(revisaoId, areaId) > 0;
    }
}

