package br.com.wisebyte.samarco.mapper.revisao;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RevisaoMapper implements EntityMapper<Revisao, RevisaoDTO> {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Revisao toEntity( RevisaoDTO dto ) {
        return Revisao.builder( )
                .id( dto.getId( ) )
                .numeroRevisao( dto.getNumeroRevisao( ) )
                .descricao( dto.getDescricao( ) )
                .oficial( dto.isOficial( ) )
                .usuario(
                        dto.getUsuarioId( ) != null
                                ? usuarioRepository.findById( dto.getUsuarioId( ) ).orElse( null )
                                : null
                )
                .planejamento(
                        dto.getPlanejamentoId( ) != null
                                ? planejamentoRepository.findById( dto.getPlanejamentoId( ) ).orElse( null )
                                : null
                )
                .build( );
    }

    @Override
    public RevisaoDTO toDTO( Revisao entity ) {
        return RevisaoDTO.builder( )
                .id( entity.getId( ) )
                .numeroRevisao( entity.getNumeroRevisao( ) )
                .descricao( entity.getDescricao( ) )
                .oficial( entity.isOficial( ) )
                .usuarioId(
                        entity.getUsuario( ) != null
                                ? entity.getUsuario( ).getUsuario( )
                                : null
                )
                .planejamentoId(
                        entity.getPlanejamento( ) != null
                                ? entity.getPlanejamento( ).getId( )
                                : null
                )
                .build( );
    }
}
