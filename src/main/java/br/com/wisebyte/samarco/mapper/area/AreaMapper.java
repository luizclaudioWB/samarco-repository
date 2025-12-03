package br.com.wisebyte.samarco.mapper.area;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AreaMapper implements EntityMapper<Area, AreaDTO> {

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Area toEntity( AreaDTO dto ) {
        return Area.builder( )
                .id( dto.getId( ) )
                .nomeArea( dto.getNomeArea( ) )
                .tipoArea( dto.getTipoArea( ) )
                .ativo( dto.isAtivo( ) )
                .unidade(
                        dto.getUnidadeId( ) != null
                                ? unidadeRepository.findById( dto.getUnidadeId( ) ).orElse( null )
                                : null
                )
                .usuario(
                        dto.getUsuarioId( ) != null
                                ? usuarioRepository.findById( dto.getUsuarioId( ) ).orElse( null )
                                : null
                )
                .build( );
    }

    @Override
    public AreaDTO toDTO( Area entity ) {
        return AreaDTO.builder( )
                .id( entity.getId( ) )
                .nomeArea( entity.getNomeArea( ) )
                .tipoArea( entity.getTipoArea( ) )
                .ativo( entity.isAtivo( ) )
                .unidadeId(
                        entity.getUnidade( ) != null
                                ? entity.getUnidade( ).getId( )
                                : null
                )
                .usuarioId(
                        entity.getUsuario( ) != null
                                ? entity.getUsuario( ).getUsuario( )
                                : null
                )
                .build( );
    }
}
