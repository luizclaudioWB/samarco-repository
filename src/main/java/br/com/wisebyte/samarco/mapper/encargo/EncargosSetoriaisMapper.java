package br.com.wisebyte.samarco.mapper.encargo;

import br.com.wisebyte.samarco.core.mapper.EntityMapper;
import br.com.wisebyte.samarco.dto.encargo.EncargosSetoriaisDTO;
import br.com.wisebyte.samarco.model.encargo.EncargosSetoriais;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EncargosSetoriaisMapper implements EntityMapper<EncargosSetoriais, EncargosSetoriaisDTO> {

    @Inject
    RevisaoRepository revisaoRepository;

    @Override
    public EncargosSetoriais toEntity(EncargosSetoriaisDTO dto) {
        return EncargosSetoriais.builder()
                .id(dto.getId())
                .revisao(dto.getRevisaoId() != null
                        ? revisaoRepository.findById(dto.getRevisaoId()).orElse(null) : null)
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
    }

    @Override
    public EncargosSetoriaisDTO toDTO(EncargosSetoriais entity) {
        return EncargosSetoriaisDTO.builder()
                .id(entity.getId())
                .revisaoId(entity.getRevisao() != null ? entity.getRevisao().getId() : null)
                .eerJaneiro(entity.getEerJaneiro())
                .eerFevereiro(entity.getEerFevereiro())
                .eerMarco(entity.getEerMarco())
                .eerAbril(entity.getEerAbril())
                .eerMaio(entity.getEerMaio())
                .eerJunho(entity.getEerJunho())
                .eerJulho(entity.getEerJulho())
                .eerAgosto(entity.getEerAgosto())
                .eerSetembro(entity.getEerSetembro())
                .eerOutubro(entity.getEerOutubro())
                .eerNovembro(entity.getEerNovembro())
                .eerDezembro(entity.getEerDezembro())
                .ercapJaneiro(entity.getErcapJaneiro())
                .ercapFevereiro(entity.getErcapFevereiro())
                .ercapMarco(entity.getErcapMarco())
                .ercapAbril(entity.getErcapAbril())
                .ercapMaio(entity.getErcapMaio())
                .ercapJunho(entity.getErcapJunho())
                .ercapJulho(entity.getErcapJulho())
                .ercapAgosto(entity.getErcapAgosto())
                .ercapSetembro(entity.getErcapSetembro())
                .ercapOutubro(entity.getErcapOutubro())
                .ercapNovembro(entity.getErcapNovembro())
                .ercapDezembro(entity.getErcapDezembro())
                .essJaneiro(entity.getEssJaneiro())
                .essFevereiro(entity.getEssFevereiro())
                .essMarco(entity.getEssMarco())
                .essAbril(entity.getEssAbril())
                .essMaio(entity.getEssMaio())
                .essJunho(entity.getEssJunho())
                .essJulho(entity.getEssJulho())
                .essAgosto(entity.getEssAgosto())
                .essSetembro(entity.getEssSetembro())
                .essOutubro(entity.getEssOutubro())
                .essNovembro(entity.getEssNovembro())
                .essDezembro(entity.getEssDezembro())
                .build();
    }
}
