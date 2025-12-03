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
    public Unidade toEntity( UnidadeDTO dto ) {
        return Unidade.builder( )
                .id( dto.getId( ) )
                .nomeUnidade( dto.getNome( ) )
                .unidadeGeradora( dto.getGeraEnergia( ) )
                .conectadaRedeBasica( dto.getConectadaRedeBasica( ) )
                .unidadeRecebedoraCreditosDeInjecao( dto.getUnidadeRecebedoraCreditosDeInjecao( ) != null ? unidadeRepository.findById( dto.getUnidadeRecebedoraCreditosDeInjecao( ) ).orElse( null ) : null )
                .estado( dto.getEstado( ) )
                .build( );
    }

    @Override
    public UnidadeDTO toDTO( Unidade entity ) {
        return UnidadeDTO.builder( )
                .id( entity.getId( ) )
                .nome( entity.getNomeUnidade( ) )
                .conectadaRedeBasica( entity.getConectadaRedeBasica( ) )
                .geraEnergia( entity.getUnidadeGeradora( ) )
                .unidadeRecebedoraCreditosDeInjecao( entity.getUnidadeRecebedoraCreditosDeInjecao( ) != null ? entity.getUnidadeRecebedoraCreditosDeInjecao( ).getId( ) : null )
                .estado( entity.getEstado( ) )
                .build( );
    }
}
