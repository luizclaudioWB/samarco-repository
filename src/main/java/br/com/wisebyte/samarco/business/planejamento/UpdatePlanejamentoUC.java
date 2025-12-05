package br.com.wisebyte.samarco.business.planejamento;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.planejamento.PlanejamentoDTO;
import br.com.wisebyte.samarco.mapper.planejamento.PlanejamentoMapper;
import br.com.wisebyte.samarco.model.planejamento.Planejamento;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class UpdatePlanejamentoUC {

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    PlanejamentoMapper planejamentoMapper;

    @Inject
    PlanejamentoValidationBusiness validator;


    @Transactional
    public PlanejamentoDTO update(@NotNull PlanejamentoDTO dto) {

        if (validator.idIsNull(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Planejamento Id",
                    "Id do Planejamento não deve ser nulo para atualização"
            );
        }

        if (!validator.existePlanejamento(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Planejamento Id",
                    "Planejamento não encontrado"
            );
        }

        if (!validator.anoValido(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Ano",
                    "Ano do planejamento é inválido ou está fora do intervalo permitido (2000-2100)"
            );
        }

        //  Não pode existir OUTRO planejamento com o mesmo ano
        // (permitimos atualizar o ano do próprio planejamento)
        if (validator.existeOutroPlanejamentoParaAno(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Ano",
                    "Já existe outro planejamento cadastrado para o ano " + dto.getAno()
            );
        }

        // Descrição deve ser válida (não nula e não vazia)
        if (!validator.descricaoValida(dto)) {
            throw new ValidadeExceptionBusiness(
                    "Planejamento",
                    "Descrição",
                    "Descrição do planejamento é obrigatória"
            );
        }

        Planejamento planejamento = planejamentoRepository.findById(dto.getId())
                .orElseThrow();

        applyNewValues(planejamento, dto);

        Planejamento saved = planejamentoRepository.save(planejamento);

        return planejamentoMapper.toDTO(saved);
    }

    private void applyNewValues(Planejamento planejamento, PlanejamentoDTO dto) {
        planejamento.setAno(dto.getAno());
        planejamento.setDescricao(dto.getDescricao());
        planejamento.setMensagem(dto.getMensagem());
        // Campo 'corrente' é calculado via @Formula, não pode ser atualizado manualmente
    }
}
