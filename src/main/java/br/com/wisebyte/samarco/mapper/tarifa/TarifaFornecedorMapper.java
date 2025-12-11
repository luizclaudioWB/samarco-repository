package br.com.wisebyte.samarco.mapper.tarifa;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.tarifa.TarifaFornecedorDTO;
import br.com.wisebyte.samarco.model.planejamento.tarifa.TarifaFornecedor;
import br.com.wisebyte.samarco.repository.fornecedor.FornecedorRepository;
import br.com.wisebyte.samarco.repository.tarifa.TarifaPlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TarifaFornecedorMapper implements EntityMapper<TarifaFornecedor, TarifaFornecedorDTO> {

    @Inject
    TarifaPlanejamentoRepository tarifaPlanejamentoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Override
    public TarifaFornecedorDTO toDTO(TarifaFornecedor entity) {
        if (entity == null) {
            return null;
        }

        return TarifaFornecedorDTO.builder()
                .id(entity.getId())
                .tarifaPlanejamentoId( entity.getPlanejamento( ) != null ? entity.getPlanejamento( ).getId( ) : null )
                .fornecedorId( entity.getFornecedor( ) != null ? entity.getFornecedor( ).getId( ) : null )
                .ipcaRealizada(entity.getIpcaRealizada())
                .ipcaProjetado(entity.getIpcaProjetado())
                .montante(entity.getMontante())
                .build();
    }

    @Override
    public TarifaFornecedor toEntity(TarifaFornecedorDTO dto) {
        if (dto == null) {
            return null;
        }
        TarifaFornecedor entity = new TarifaFornecedor();
        if (dto.getTarifaPlanejamentoId() != null) {
            entity.setPlanejamento( tarifaPlanejamentoRepository.findById( dto.getTarifaPlanejamentoId( ) ).orElse( null ) );
        }
        if (dto.getFornecedorId() != null) {
            entity.setFornecedor( fornecedorRepository.findById( dto.getFornecedorId( ) ).orElse( null ) );
        }
        entity.setIpcaRealizada(dto.getIpcaRealizada());
        entity.setIpcaProjetado(dto.getIpcaProjetado());
        entity.setMontante(dto.getMontante());
        return entity;
    }
}
