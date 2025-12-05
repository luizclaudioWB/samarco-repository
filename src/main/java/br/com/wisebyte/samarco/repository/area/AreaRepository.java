package br.com.wisebyte.samarco.repository.area;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.area.TipoArea;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AreaRepository extends BasicRepository<Area, Long> {

    @Find
    Stream<Area> findAll( );

    @Find
    List<Area> findByUnidade( Unidade unidade );

    @Find
    List<Area> findByUsuario( Usuario usuario );

    @Find
    List<Area> findByAtivo( boolean ativo );

    @Find
    List<Area> findByTipoArea( TipoArea tipoArea );
}
