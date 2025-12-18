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
import jakarta.validation.constraints.NotNull;

@ApplicationScoped
public class CreateRevisaoUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    RevisaoMapper revisaoMapper;

    @Inject
    RevisaoValidationBusiness validatorBusiness;

    @Inject
    PlanejamentoRepository planejamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;


    @Transactional
    public RevisaoDTO create( @NotNull RevisaoInputDTO inputDTO ) {
        validate( inputDTO );
        return createInternal(
                inputDTO.getNumeroRevisao( ),
                inputDTO.getUsuarioId( ),
                inputDTO.getPlanejamentoId( ),
                inputDTO.getDescricao( ),
                false,
                false
        );
    }

    public void validate( @NotNull RevisaoInputDTO dto ) {
        validate( dto.getNumeroRevisao( ), dto.getUsuarioId( ), dto.getPlanejamentoId( ), dto.getDescricao( ) );
    }

    @Transactional
    public RevisaoDTO createInternal(
            Integer numeroRevisao,
            String usuarioId,
            Long planejamentoId,
            String descricao,
            boolean oficial,
            boolean finished ) {
        validate( numeroRevisao, usuarioId, planejamentoId, descricao );
        Revisao revisao = Revisao.builder( )
                .numeroRevisao( numeroRevisao )
                .usuario( usuarioRepository.findById( usuarioId ).orElseThrow( ) )
                .planejamento( planejamentoRepository.findById( planejamentoId ).orElseThrow( ) )
                .descricao( descricao )
                .oficial( oficial )
                .finished( finished )
                .build( );
        Revisao saved = revisaoRepository.save( revisao );
        return revisaoMapper.toDTO( saved );
    }

    public void validate( Integer numeroRevisao,
                          String usuarioId,
                          Long planejamentoId,
                          String descricao ) {
        if ( !validatorBusiness.planningExists( planejamentoId ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Planejamento", "Planejamento não encontrado" );
        }

        if ( !validatorBusiness.userExists( usuarioId ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Usuario", "Usuário não encontrado" );
        }

        if ( descricao == null || descricao.trim( ).isEmpty( ) ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Descricao", "Descrição da revisão é obrigatória" );
        }

        if ( numeroRevisao == null || numeroRevisao <= 0 ) {
            throw new ValidadeExceptionBusiness( "Revisao", "Numero Revisao", "Número da revisão deve ser maior que zero" );
        }
    }
}
