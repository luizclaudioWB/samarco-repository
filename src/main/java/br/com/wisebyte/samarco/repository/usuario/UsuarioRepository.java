package br.com.wisebyte.samarco.repository.usuario;

import br.com.wisebyte.samarco.model.usuario.Usuario;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BasicRepository<Usuario, String> {

    @Query( "SELECT u FROM Usuario u WHERE u.usuario = :usuario" )
    Optional<Usuario> findByUsuario( String usuario );
}
