package br.com.wisebyte.samarco.business.encargo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import br.com.wisebyte.samarco.mapper.encargo.EncargosSetoriaisMapper;
import br.com.wisebyte.samarco.model.encargo.EncargosSetoriais;
import br.com.wisebyte.samarco.repository.encargo.EncargosSetoriaisRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class CreateEncargosSetoriaisUC {

    @Inject
    EncargosSetoriaisRepository encargosRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    EncargosSetoriaisMapper mapper;

    @Transactional
    public EncargosSetoriaisDTO execute(@Valid EncargosSetoriaisDTO dto) {
        var revisao = revisaoRepository.findById(dto.getRevisaoId())
                .orElseThrow(() -> new ValidadeExceptionBusiness("Encargos", "Revisao", "Revisao nao encontrada"));

        if (encargosRepository.findByRevisaoId(dto.getRevisaoId()).isPresent()) {
            throw new ValidadeExceptionBusiness("Encargos", "Encargos", "Ja existem encargos para esta revisao");
        }

        var entity = EncargosSetoriais.builder()
                .revisao(revisao)
                .eerJaneiro(dto.getEerJaneiro())
                .eerFevereiro(dto.getEerFevereiro())
                .eerMarco(dto.getEerMarco())
                .eerAbril(dto.getEerAbril())
                .eerMaio(dto.getEerMaio())
                .eerJunho(dto.getEerJunho())
                .eerJulho(dto.getEerJulho())
                .eerAgosto(dto.getEerAgosto())
                .eerSetembro(dto.getEerSetembro())
                .eerOutubro(dto.getEerOutubro())
                .eerNovembro(dto.getEerNovembro())
                .eerDezembro(dto.getEerDezembro())
                .ercapJaneiro(dto.getErcapJaneiro())
                .ercapFevereiro(dto.getErcapFevereiro())
                .ercapMarco(dto.getErcapMarco())
                .ercapAbril(dto.getErcapAbril())
                .ercapMaio(dto.getErcapMaio())
                .ercapJunho(dto.getErcapJunho())
                .ercapJulho(dto.getErcapJulho())
                .ercapAgosto(dto.getErcapAgosto())
                .ercapSetembro(dto.getErcapSetembro())
                .ercapOutubro(dto.getErcapOutubro())
                .ercapNovembro(dto.getErcapNovembro())
                .ercapDezembro(dto.getErcapDezembro())
                .essJaneiro(dto.getEssJaneiro())
                .essFevereiro(dto.getEssFevereiro())
                .essMarco(dto.getEssMarco())
                .essAbril(dto.getEssAbril())
                .essMaio(dto.getEssMaio())
                .essJunho(dto.getEssJunho())
                .essJulho(dto.getEssJulho())
                .essAgosto(dto.getEssAgosto())
                .essSetembro(dto.getEssSetembro())
                .essOutubro(dto.getEssOutubro())
                .essNovembro(dto.getEssNovembro())
                .essDezembro(dto.getEssDezembro())
                .build();

        encargosRepository.save(entity);
        return mapper.toDTO(entity);
    }
}
