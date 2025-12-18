package br.com.wisebyte.samarco.business.revisao;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.revisao.RevisaoDTO;
import br.com.wisebyte.samarco.dto.revisao.RevisaoInputDTO;
import br.com.wisebyte.samarco.mapper.revisao.RevisaoMapper;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.repository.planejamento.PlanejamentoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import br.com.wisebyte.samarco.repository.usuario.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateRevisaoUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    @Inject
    RevisaoValidationBusiness validator;

    @Transactional
    public RevisaoDTO update(RevisaoInputDTO inputDTO) {
        if (inputDTO.getId() == null) {
            throw new ValidadeExceptionBusiness("Revisao", "Revisao Id", "Id da Revisão não deve ser nulo para atualização");
        }

        Revisao revisao = revisaoRepository.findById( inputDTO.getId( ) ).orElseThrow( ( ) -> new ValidadeExceptionBusiness( "Revisao", "Revisao Id", "Revisão não encontrada" ) );
        validate( revisao, inputDTO );


        applyNewValues(revisao, inputDTO);
        return revisaoMapper.toDTO(revisaoRepository.save(revisao));
    }

    public void validate( Revisao revisao, RevisaoInputDTO inputDTO ) {
        if ( revisao.isFinished( ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Finished", "Revisão finalizada não pode ser editada. Depois de finalizado, NINGUÉM mais mexe (nem o admin)." );
        }

        if ( !validator.planningExists( inputDTO.getPlanejamentoId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Planejamento", "Planejamento não encontrado" );
        }

        if ( !validator.userExists( inputDTO.getUsuarioId( ) ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Usuario", "Usuário não encontrado" );
        }

        if ( inputDTO.getDescricao( ) == null || inputDTO.getDescricao( ).trim( ).isEmpty( ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Descricao", "Descrição da revisão é obrigatória" );
        }

        if ( inputDTO.getNumeroRevisao( ) == null || inputDTO.getNumeroRevisao( ) <= 0 ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Numero Revisao", "Número da revisão deve ser maior que zero" );
        }
    }

    private void applyNewValues(Revisao revisao, RevisaoInputDTO inputDTO) {
        revisao.setNumeroRevisao(inputDTO.getNumeroRevisao());
        revisao.setDescricao(inputDTO.getDescricao());
        revisao.setPlanejamento( inputDTO.getPlanejamentoId( ) != null ? planejamentoRepository.findById( inputDTO.getPlanejamentoId( ) ).orElse( null ) : null );
        revisao.setUsuario( inputDTO.getUsuarioId( ) != null ? usuarioRepository.findById( inputDTO.getUsuarioId( ) ).orElse( null ) : null );
    }
}
