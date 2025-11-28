package br.com.wisebyte.samarco.business.area;

import br.com.wisebyte.samarco.dto.area.AreaDTO;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AreaValidatorBusiness {

    @Inject
    AreaRepository areaRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public boolean idIsNull( AreaDTO dto ) {
        return dto.getId( ) == null;
    }

    public boolean existeArea( AreaDTO dto ) {
        return areaRepository.findById( dto.getId( ) ).isPresent( );
    }

    public boolean existeUnidade( Long unidadeId ) {
        if ( unidadeId == null ) return false;
        return unidadeRepository.findById( unidadeId ).isPresent( );
    }

    public boolean existeUsuario( String usuarioId ) {
        if ( usuarioId == null ) return false;
        return usuarioRepository.findById( usuarioId ).isPresent( );
    }
}
