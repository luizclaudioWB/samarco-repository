package br.com.wisebyte.samarco.business.tarifa.table;

import br.com.wisebyte.samarco.model.unidade.Unidade;
import br.com.wisebyte.samarco.repository.unidade.UnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class CalcTarifaDistribuidoraUC {

    @Inject
    UnidadeRepository unidadeRepository;

    public void calcTable( @NotNull Long revisaoId ) {
        List<Unidade> list = unidadeRepository.findAll( ).toList( );

    }

    /*
     * Calcula para todas as unidades
     */
    public void calcPonta( ) {

    }

    /*
     * Calcula para todas as unidades
     */
    public void calcForaPonta( ) {

    }

    /*
     * Calcula somente para unidades conectadas na rede baisca
     */
    public void calcEncargoTransmissao( ) {

    }

    /*
     * Calcula somente para unidades que recebem creditos de injeção
     */
    public void calcDescontoGeracao( ) {

    }

    /*
     * Calcula somente para unidades que nao estejam conectadas a rede basica
     */
    public void calcEncargoDistribuicao( ) {

    }

    /*
     *  Calculo geral por estado
     */
    public void calcPMIX( ) {

    }
}
