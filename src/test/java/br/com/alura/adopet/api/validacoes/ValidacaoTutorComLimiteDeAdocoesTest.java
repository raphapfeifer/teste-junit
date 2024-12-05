package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;



    @Test
    void naoDevePermitirSolicitacaoDeAdocaoTutorAtingiuLimite5Adocoes(){

        given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(5);

        assertThrows(ValidacaoException.class,() ->validador.validar(dto));

    }

    @Test
    void devePermitirSolicitacaoDeAdocaoTutorNaoAtingiuLimite5Adocoes(){

        given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(4);

        assertDoesNotThrow(() ->validador.validar(dto));

    }

}