package br.com.wisebyte.samarco;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.model.usuario.Usuario;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static br.com.wisebyte.samarco.model.area.TipoArea.*;

@QuarkusTest
public class CadastrosTest {

    @Inject
    EntityManager entityManager;

    void criaUsuario( ) {
        entityManager.persist(
                Usuario.builder( )
                        .nome( "Leandro" )
                        .usuario( "leandro@samarco.com.br" )
                        .build( )
        );
    }

    void criaUnidades( ) {
        Unidade guilmanAmorim = Unidade.builder( )
                .nomeUnidade( "Guilman Amorim" )
                .unidadeGeradora( true )
                .conectadaRedeBasica( false )
                .build( );
        guilmanAmorim = entityManager.merge( guilmanAmorim );
        Unidade germano = Unidade.builder( )
                .nomeUnidade( "Germano" )
                .unidadeGeradora( false )
                .conectadaRedeBasica( false )
                .unidadeRecebedoraCreditosDeInjecao( guilmanAmorim )
                .build( );
        entityManager.persist( germano );
        Unidade matipo = Unidade.builder( )
                .nomeUnidade( "Matipó" )
                .unidadeGeradora( false )
                .conectadaRedeBasica( false )
                .build( );
        entityManager.persist( matipo );
        Unidade munizFreire = Unidade.builder( )
                .nomeUnidade( "Muniz Freire" )
                .unidadeGeradora( true )
                .conectadaRedeBasica( false )
                .build( );
        munizFreire = entityManager.merge( munizFreire );
        Unidade ubu = Unidade.builder( )
                .nomeUnidade( "UBU" )
                .unidadeGeradora( false )
                .unidadeRecebedoraCreditosDeInjecao( munizFreire )
                .conectadaRedeBasica( false )
                .build( );
        entityManager.persist( ubu );
    }

    void criaPlanejamento( ) {
        entityManager.merge(
                Planejamento.builder( )
                        .ano( Short.valueOf( "2026" ).intValue( ) )
                        .corrente( true )
                        .descricao( "Planejamento Principal do ano de 2026" )
                        .mensagem( "Planejamento Principal do ano de 2026" )
                        .build( )
        );
    }

    void criaRevisao( ) {
        entityManager.persist(
                Revisao.builder( )
                        .numeroRevisao( Short.valueOf( "1" ).intValue( ) )
                        .planejamento( entityManager.find( Planejamento.class, 1L ) )
                        .oficial( true )
                        .descricao( "Primeiro Planejamento de 2026" )
                        .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                        .build( )
        );
    }

    public void criaAreasDeGermano() {
        Area filtragem = Area.builder( )
                .nomeArea( "Filtragem Germano (Arenoso)" )
                .unidade( entityManager.find( Unidade.class, 2L ) )
                .tipoArea( FILTRAGEM )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( filtragem );
        Area usina1 = Area.builder( )
                .nomeArea( "Beneficiamento Usina 1" )
                .unidade( entityManager.find( Unidade.class, 2L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina1 );
        Area usina2 = Area.builder( )
                .nomeArea( "Beneficiamento Usina 2" )
                .unidade( entityManager.find( Unidade.class, 2L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina2 );
        Area usina3 = Area.builder( )
                .nomeArea( "Beneficiamento Usina 3" )
                .unidade( entityManager.find( Unidade.class, 2L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina3 );
    }

    public void criaAreasMatipo( ) {
        Area area1 = Area.builder( )
                .nomeArea( "Mineroduto 1" )
                .unidade( entityManager.find( Unidade.class, 3L ) )
                .tipoArea( MINERODUTO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( area1 );
        Area area2 = Area.builder( )
                .nomeArea( "Mineroduto 2" )
                .unidade( entityManager.find( Unidade.class, 3L ) )
                .tipoArea( MINERODUTO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( area2 );
        Area area3 = Area.builder( )
                .nomeArea( "Mineroduto 3" )
                .unidade( entityManager.find( Unidade.class, 3L ) )
                .tipoArea( MINERODUTO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( area3 );

    }

    public void criaAreasUbu( ) {
        Area preparacao1 = Area.builder( )
                .nomeArea( "Preparação 1" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( PREPARACAO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( preparacao1 );
        Area preparacao2 = Area.builder( )
                .nomeArea( "Preparação 2" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( PREPARACAO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( preparacao2 );
        Area usina1 = Area.builder( )
                .nomeArea( "Usina 1" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina1 );
        Area usina2 = Area.builder( )
                .nomeArea( "Usina 2" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina2 );
        Area usina3 = Area.builder( )
                .nomeArea( "Usina 3" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina3 );
        Area usina4 = Area.builder( )
                .nomeArea( "Usina 4" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( USINA )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( usina4 );
        Area vendas = Area.builder( )
                .nomeArea( "Vendas" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( VENDAS )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( vendas );
        Area producao = Area.builder( )
                .nomeArea( "Produção PSC + PSM" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( PRODUCAO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( producao );
        Area embarque = Area.builder( )
                .nomeArea( "Pellet Feed (embarque)" )
                .unidade( entityManager.find( Unidade.class, 5L ) )
                .tipoArea( PRODUCAO )
                .ativo( true )
                .usuario( entityManager.find( Usuario.class, "leandro@samarco.com.br" ) )
                .build( );
        entityManager.merge( embarque );
    }

    public void criaAreas( ) {
        criaAreasDeGermano( );
        criaAreasMatipo( );
        criaAreasUbu( );
    }

    /**
     * Para cada etapa tipo, demanda, produção, cadastrar quais areas participam daquela fase
     */
    @Test
    @Transactional
    public void insertData( ) {
        criaUsuario( );
        criaUnidades( );
        criaPlanejamento( );
        criaRevisao( );
        criaAreas( );
    }
}
