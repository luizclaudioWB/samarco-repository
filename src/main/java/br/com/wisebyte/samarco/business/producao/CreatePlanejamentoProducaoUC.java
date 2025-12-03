package br.com.wisebyte.samarco.business.producao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.prducao.PlanejamentoProducaoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoProducaoMapper;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class CreatePlanejamentoProducaoUC {

        @Inject
        PlanejamentoProducaoRepository repository;

        @Inject
        PlanejamentoProducaoMapper mapper;

        @Inject
        PlanejamentoProducaoValidationBusiness validator;

        @Transactional
        public PlanejamentoProducaoDTO create(@NotNull PlanejamentoProducaoDTO dto) {

            if (!validator.revisaoExists(dto.getRevisaoId())) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Revisao",
                        "Revisao nao encontrada"
                );
            }

            if (!validator.revisaoNaoFinalizada(dto.getRevisaoId())) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Revisao",
                        "Revisao finalizada nao pode ser alterada"
                );
            }

            if (!validator.areaExists(dto.getAreaId())) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Area",
                        "Area nao encontrada"
                );
            }

            if (!validator.areaAtiva(dto.getAreaId())) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Area",
                        "Area esta desativada"
                );
            }

            if (!validator.combinacaoRevisaoAreaUnica(dto)) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Revisao/Area",
                        "Ja existe producao cadastrada para esta Area nesta Revisao"
                );
            }

            if (!validator.valoresMensaisValidos(dto)) {
                throw new ValidadeExceptionBusiness(
                        "PlanejamentoProducao",
                        "Valores Mensais",
                        "Valores de producao nao podem ser negativos"
                );
            }

            PlanejamentoProducao entity = mapper.toEntity(dto);
            PlanejamentoProducao saved = repository.save(entity);
            return mapper.toDTO(saved);
        }
    }

