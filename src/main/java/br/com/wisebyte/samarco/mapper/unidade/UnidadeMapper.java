package br.com.wisebyte.samarco.mapper.unidade;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UnidadeMapper implements EntityMapper<Unidade, UnidadeDTO> {

    @Inject
    UnidadeRepository unidadeRepository;

    @Override
    public UnidadeDTO toDTO( Unidade entity ) {
        return UnidadeDTO.builder( )
                .id( entity.getId( ) )
                .nome( entity.getNomeUnidade( ) )
                .conectadaRedeBasica( entity.getConectadaRedeBasica( ) )
                .geraEnergia( entity.getUnidadeGeradora( ) )
                .unidadeCedenteCreditosDeInjecao( entity.getUnidadeCedenteCreditosDeInjecao( ) != null ? entity.getUnidadeCedenteCreditosDeInjecao( ).getId( ) : null )
                .estado( entity.getEstado( ) )
                .build( );
    }

    @Override
    public Unidade toEntity( UnidadeDTO dto ) {
        return Unidade.builder( )
                .id( dto.getId( ) )
                .nomeUnidade( dto.getNome( ) )
                .unidadeGeradora( dto.getGeraEnergia( ) )
                .conectadaRedeBasica( dto.getConectadaRedeBasica( ) )
                .unidadeCedenteCreditosDeInjecao( dto.getUnidadeCedenteCreditosDeInjecao( ) != null ? unidadeRepository.findById( dto.getUnidadeCedenteCreditosDeInjecao( ) ).orElse( null ) : null )
                .estado( dto.getEstado( ) )
                .build( );
    }
}
