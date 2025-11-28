package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RevisaoValidationBusiness {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public boolean idIsNull( RevisaoDTO dto ) {
        return dto.getId( ) == null;
    }

    public boolean existeRevisao( RevisaoDTO dto ) {
        return revisaoRepository.findById( dto.getId( ) ).isPresent( );
    }

    public boolean existePlanejamento( Long planejamentoId ) {
        if ( planejamentoId == null ) return false;
        return planejamentoRepository.findById( planejamentoId ).isPresent( );
    }

    public boolean existeUsuario( String usuarioId ) {
        if ( usuarioId == null ) return false;
        return usuarioRepository.findById( usuarioId ).isPresent( );
    }

    public boolean descricaoValida( RevisaoDTO dto ) {
        return dto.getDescricao( ) != null && !dto.getDescricao( ).trim( ).isEmpty( );
    }

    public boolean numeroRevisaoValido( RevisaoDTO dto ) {
        return dto.getNumeroRevisao( ) != null && dto.getNumeroRevisao( ) > 0;
    }
}
