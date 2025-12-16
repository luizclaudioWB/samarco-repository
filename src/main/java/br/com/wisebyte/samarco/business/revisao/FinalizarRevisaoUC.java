package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.mapper.revisao.RevisaoMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class FinalizarRevisaoUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    @Transactional
    public RevisaoDTO finalizar(Long revisaoId, String username) {
        if (revisaoId == null) {
            throw new ValidadeExceptionBusiness(
                "Revisao",
                "Revisao Id",
                "ID da revisão é obrigatório para finalizar"
            );
        }

        Revisao revisao = revisaoRepository.findById(revisaoId)
            .orElseThrow(() -> new ValidadeExceptionBusiness(
                "Revisao",
                "Revisao Id",
                "Revisão não encontrada"
            ));

        if (revisao.isFinished()) {
            throw new ValidadeExceptionBusiness(
                "Revisao",
                "Finished",
                "Revisão já está finalizada"
            );
        }

        revisao.setFinished(true);

        Revisao saved = revisaoRepository.save(revisao);

        Log.infof( "[REVISAO] Finalizada: id=%d, planejamento=%d, numero=%d, usuario=%s", saved.getId( ), saved.getPlanejamento( ).getId( ), saved.getNumeroRevisao( ), username );

        return revisaoMapper.toDTO(saved);
    }
}
