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

    public void calcPonta( ) {

    }

    public void calcForaPonta( ) {

    }

    public void calcEncargoTransmissao( ) {

    }

    public void calcDescontoGeracao( ) {

    }

    public void calcEncargoDistribuicao( ) {

    }

    public void calcPMIX( ) {

    }
}
