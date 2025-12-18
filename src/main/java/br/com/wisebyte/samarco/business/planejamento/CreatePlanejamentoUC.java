package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.business.revisao.CreateRevisaoUC;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;


@ApplicationScoped
public class CreatePlanejamentoUC {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoMapper planejamentoMapper;

    @Inject
    PlanejamentoValidationBusiness validatorBusiness;

    @Inject
    CreateRevisaoUC createRevisaoUC;


    @Transactional
    public PlanejamentoDTO create(@NotNull PlanejamentoDTO dto, String username) {
        validate( dto, username );
        Planejamento planejamento = planejamentoMapper.toEntity( dto );
        Planejamento saved = planejamentoRepository.save( planejamento );
        createRevisaoUC.createInternal(
                1,
                username,
                saved.getId( ),
                "Revisão Oficial",
                true,
                false
        );
        return planejamentoMapper.toDTO( saved );
    }

    public void validate( @NotNull PlanejamentoDTO dto, String username ) {
        if (!validatorBusiness.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness( "Planejamento", "Planejamento Id", "Id do Planejamento deve ser nulo para criação" );
        }

        if (!validatorBusiness.isYearValid(dto)) {
            throw new ValidadeExceptionBusiness( "Planejamento", "Ano", "Ano do planejamento é inválido ou está fora do intervalo permitido (2000-2100)" );
        }

        if (validatorBusiness.planningExistsForYear(dto)) {
            throw new ValidadeExceptionBusiness( "Planejamento", "Ano", "Já existe um planejamento cadastrado para o ano " + dto.getAno( ) );
        }

        if (!validatorBusiness.isDescriptionValid(dto)) {
            throw new ValidadeExceptionBusiness( "Planejamento", "Descrição", "Descrição do planejamento é obrigatória" );
        }

        if (username == null || username.trim().isEmpty()) {
            throw new ValidadeExceptionBusiness( "Planejamento", "Usuario", "ID do usuário é obrigatório para criar o planejamento" );
        }
    }
}
