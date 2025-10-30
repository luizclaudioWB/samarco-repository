package br.com.wisebyte.samarco;

import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.lang.Double.parseDouble;
import static java.math.BigDecimal.valueOf;

@QuarkusTest
public class PlanejamentoProducaoTest {

    @Inject
    EntityManager entityManager;


    public void cadastraValoresAreaMes( Revisao revisao, Long idArea,
                                        String valorJaneiro, String valorFevereiro,
                                        String valorMarco, String valorAbril,
                                        String valorMaio, String valorJunho,
                                        String valorJulho, String valorAgosto,
                                        String valorSetembro, String valorOutubro,
                                        String valorNovembro, String valorDezembro ) {
        Area area = entityManager.find( Area.class, idArea );
        PlanejamentoProducao planejamento = PlanejamentoProducao.builder( )
                .revisao( revisao )
                .area( area )
                .valorJaneiro( valueOf( parseDouble( valorJaneiro ) ) )
                .valorFevereiro( valueOf( parseDouble( valorFevereiro ) ) )
                .valorMarco( valueOf( parseDouble( valorMarco ) ) )
                .valorAbril( valueOf( parseDouble( valorAbril ) ) )
                .valorMaio( valueOf( parseDouble( valorMaio ) ) )
                .valorJunho( valueOf( parseDouble( valorJunho ) ) )
                .valorJulho( valueOf( parseDouble( valorJulho ) ) )
                .valorAgosto( valueOf( parseDouble( valorAgosto ) ) )
                .valorSetembro( valueOf( parseDouble( valorSetembro ) ) )
                .valorOutubro( valueOf( parseDouble( valorOutubro ) ) )
                .valorNovembro( valueOf( parseDouble( valorNovembro ) ) )
                .valorDezembro( valueOf( parseDouble( valorDezembro ) ) )
                .build( );
        entityManager.persist( planejamento );
    }

    @Test
    @Transactional
    public void planejamentoProducao2026( ) {
        Revisao revisao = entityManager.find( Revisao.class, 1L );
        cadastraValoresAreaMes( revisao, 1L,
                "1059.0684797126", "1021.0320458292", "1004.4547012103", "1119.8199826745",
                "1137.3516848964", "1148.2279286749", "1188.5717781008", "1138.5113068253",
                "1149.7829779954", "1159.5339246845", "1051.4633909269", "1120.4827388095"
        );
        cadastraValoresAreaMes( revisao, 2L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 3L,
                "599.7770139889", "546.4730463379", "576.6035934941", "565.6074098386",
                "584.4284841540", "619.5652631540", "641.8896331673", "641.7021923234",
                "624.3490803859", "611.9833393538", "525.6235565250", "585.6058415924"
        );
        cadastraValoresAreaMes( revisao, 4L,
                "660.8133949243", "683.6218453129", "680.4739465026", "806.8385570883",
                "828.4632735670", "802.1925348498", "822.4265876620", "751.4349254645",
                "793.4527548283", "794.8184911124", "734.2425186755", "726.8402629772"
        );
        cadastraValoresAreaMes( revisao, 5L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 6L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 7L,
                "1260.5904089133", "1230.0948916508", "1257.0775399967", "1372.4459669269",
                "1412.8917577210", "1421.7577980039", "1464.3162208293", "1393.1371177879",
                "1417.8018352142", "1406.8018304661", "1259.8660752006", "1312.4461045696"
        );
        cadastraValoresAreaMes( revisao, 8L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 9L,
                "1260.5904089133", "1230.0948916508", "1257.0775399968", "1372.4459669269",
                "1412.8917577210", "1421.7577980039", "1464.3162208293", "1393.1371177879",
                "1417.8018352142", "1406.8018304661", "1259.8660752006", "1312.4461045696"
        );
        cadastraValoresAreaMes( revisao, 10L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 11L,
                "0","0","0","0","0","0",
                "0","0","0","0","0","0"
        );
        cadastraValoresAreaMes( revisao, 12L,
                "474.3908944034", "457.3199464420", "385.0516492992", "522.0097639304",
                "527.2109385381", "532.4677823244", "560.3159552504", "527.2854404066",
                "523.7439786370", "536.1045135161", "471.7197415254", "488.4641295179"
        );
        cadastraValoresAreaMes( revisao, 13L,
                "592.8375432252", "551.3310958747", "678.8430548739", "651.5741513953",
                "637.6812723124", "687.9947723637", "700.1088923893", "636.3548537487",
                "675.2506983775", "669.7877969993", "565.4283974811", "628.8255737571"
        );
        cadastraValoresAreaMes( revisao, 13L,
                "1067.2284376286", "1008.6510423167", "1063.8947041731", "1173.5839153257",
                "1164.8922108505", "1220.4625546881", "1260.4248476397", "1163.6402941553",
                "1198.9946770145", "1205.8923105153", "1037.1481390065", "1117.2897032750"
        );
        cadastraValoresAreaMes( revisao, 14L,
                "143.8550559387", "174.6528593809", "143.8250523376", "144.8122552379",
                "193.9590298977", "145.2341629922", "145.5938236288", "176.0477626474",
                "162.9409520931", "145.1030307946", "174.9093332511", "144.3056073295"
        );
        cadastraValoresAreaMes( revisao, 2L,
                "385.9268830000", "385.9268830000", "385.9268830000", "385.9268830000",
                "385.9268830000", "385.9268830000", "385.9268830000", "385.9268830000",
                "385.9268830000", "385.9268830000", "385.9268830000", "385.9268830000"
        );

    }
}
