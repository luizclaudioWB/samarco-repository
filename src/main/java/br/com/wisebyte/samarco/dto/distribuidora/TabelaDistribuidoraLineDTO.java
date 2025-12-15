package br.com.wisebyte.samarco.dto.distribuidora;

import br.com.wisebyte.samarco.dto.unidade.UnidadeDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabelaDistribuidoraLineDTO {

    public UnidadeDTO unidade;

    public ServicoDTO servico;

    public HorarioDTO horario;

    private BigDecimal valorJaneiro;

    private BigDecimal valorFevereiro;

    private BigDecimal valorMarco;

    private BigDecimal valorAbril;

    private BigDecimal valorMaio;

    private BigDecimal valorJunho;

    private BigDecimal valorJulho;

    private BigDecimal valorAgosto;

    private BigDecimal valorSetembro;

    private BigDecimal valorOutubro;

    private BigDecimal valorNovembro;

    private BigDecimal valorDezembro;


    public enum ServicoDTO {
        TUSD_FIO_DEMANDA( "50610003" ),
        TUSD_ENCARGO( "50610006" ),
        CONSUMO_ENERGIA( "50610002" );

        public String contaRazao;

        ServicoDTO( String contaRazao ) {
            this.contaRazao = contaRazao;
        }

        public String getContaRazao( ) {
            return contaRazao;
        }
    }

    public enum HorarioDTO {
        PONTA( ServicoDTO.TUSD_FIO_DEMANDA, 1 ),
        FORA_PONTA( ServicoDTO.TUSD_FIO_DEMANDA, 2 ),
        ENCARGO_TRANSMISSAO( ServicoDTO.TUSD_ENCARGO, 3 ),
        DESCONTO_ENERGIA_GERADA( ServicoDTO.TUSD_ENCARGO, 4 ),
        ENCARGO_DISTRIBUICAO( ServicoDTO.TUSD_ENCARGO, 5 ),
        PMIX( ServicoDTO.CONSUMO_ENERGIA, 6 );

        private final ServicoDTO servicoDTO;

        private final int sequence;

        HorarioDTO( ServicoDTO servicoDTO, int sequence ) {
            this.servicoDTO = servicoDTO;
            this.sequence = sequence;
        }

        public ServicoDTO getServicoDTO( ) {
            return servicoDTO;
        }

        public int getSequence( ) {
            return sequence;
        }
    }

}
