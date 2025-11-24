package br.com.wisebyte.samarco.mapper.distribuidora;

import br.com.wisebyte.samarco.dto.distribuidora.DistribuidoraDTO;
import br.com.wisebyte.samarco.model.distribuidora.Distribuidora;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DistribuidoraMapper {

    @Inject
    UnidadeRepository unidadeRepository;

    public Distribuidora toEntity( DistribuidoraDTO dto ) {
        return Distribuidora.builder( )
                .id( dto.getId( ) )
                .nome( dto.getNome( ) )
                .cnpj( dto.getCnpj( ) )
                .siglaAgente( dto.getSiglaAgente( ) )
                .estado( dto.getEstado( ) )
                .build( );
    }

    public DistribuidoraDTO toDTO( Distribuidora entity ) {
        return DistribuidoraDTO.builder( )
                .id( entity.getId( ) )
                .nome( entity.getNome( ) )
                .cnpj( entity.getCnpj( ) )
                .siglaAgente( entity.getSiglaAgente( ) )
                .estado( entity.getEstado( ) )
                .build( );
    }
}
