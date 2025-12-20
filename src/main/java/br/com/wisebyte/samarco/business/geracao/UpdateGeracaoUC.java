package br.com.wisebyte.samarco.business.geracao;

import br.com.wisebyte.samarco.dto.geracao.GeracaoDTO;
import br.com.wisebyte.samarco.mapper.geracao.GeracaoMapper;
import br.com.wisebyte.samarco.model.geracao.Geracao;
import br.com.wisebyte.samarco.repository.geracao.GeracaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateGeracaoUC {

    @Inject
    GeracaoRepository geracaoRepository;

    @Inject
    GeracaoMapper geracaoMapper;

    @Inject
    GeracaoValidationBusiness validationBusiness;

    @Transactional
    public GeracaoDTO execute(GeracaoDTO dto) {
        validationBusiness.validateForUpdate(dto);

        Geracao entity = geracaoMapper.toEntity(dto);
        Geracao saved = geracaoRepository.save(entity);

        return geracaoMapper.toDTO(saved);
    }
}
