package br.com.wisebyte.samarco.mapper.tarifa;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.tarifa.TarifaDistribuidoraDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaDistribuidora;
import br.com.wisebyte.samarco.repository.distribuidora.DistribuidoraRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TarifaDistribuidoraMapper implements EntityMapper<TarifaDistribuidora, TarifaDistribuidoraDTO> {

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    DistribuidoraRepository distribuidoraRepository;

    @Override
    public TarifaDistribuidora toEntity(TarifaDistribuidoraDTO dto) {
        return TarifaDistribuidora.builder()
                .id(dto.getId())
                .planejamento(
                        dto.getTarifaPlanejamentoId() != null
                                ? tarifaPlanejamentoRepository
                                    .findById(dto.getTarifaPlanejamentoId())
                                    .orElse(null)
                                : null
                )
                .distribuidora(
                        dto.getDistribuidoraId() != null
                                ? distribuidoraRepository
                                    .findById(dto.getDistribuidoraId())
                                    .orElse(null)
                                : null
                )
                .periodoInicial(dto.getPeriodoInicial())
                .periodoFinal(dto.getPeriodoFinal())
                .valorPonta(dto.getValorPonta())
                .valorForaPonta(dto.getValorForaPonta())
                .valorEncargos(dto.getValorEncargos())
                .valorEncargosAutoProducao(dto.getValorEncargosAutoProducao())
                .percentualPisCofins(dto.getPercentualPisCofins())
                .sobrescreverICMS(dto.isSobrescreverICMS())
                .percentualICMS(dto.getPercentualICMS())
                .qtdeDeHorasPonta(dto.getQtdeDeHorasPonta())
                .build();
    }

    @Override
    public TarifaDistribuidoraDTO toDTO(TarifaDistribuidora entity) {
        return TarifaDistribuidoraDTO.builder()
                .id(entity.getId())
                .tarifaPlanejamentoId(
                        entity.getPlanejamento() != null
                                ? entity.getPlanejamento().getId()
                                : null
                )
                .distribuidoraId(
                        entity.getDistribuidora() != null
                                ? entity.getDistribuidora().getId()
                                : null
                )
                .periodoInicial(entity.getPeriodoInicial())
                .periodoFinal(entity.getPeriodoFinal())
                .valorPonta(entity.getValorPonta())
                .valorForaPonta(entity.getValorForaPonta())
                .valorEncargos(entity.getValorEncargos())
                .valorEncargosAutoProducao(entity.getValorEncargosAutoProducao())
                .percentualPisCofins(entity.getPercentualPisCofins())
                .sobrescreverICMS(entity.isSobrescreverICMS())
                .percentualICMS(entity.getPercentualICMS())
                .qtdeDeHorasPonta(entity.getQtdeDeHorasPonta())
                .build();
    }
}
