package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Spy
    private List<Adocao> listaDeAdocoes = new ArrayList<>();

    @Spy
    private Adocao adocao;

    @Mock
    private SolicitacaoAdocaoDto dto;



    @Test
    void naoDevePermitirSolicitacaoDeAdocaoTutorAtingiuLimite5Adocoes(){


        adocao = new Adocao(tutor, pet, "Motivo qualquer");
        adocao.marcarComoAprovada();
        for(int i = 0; i < 5; i++) {
            listaDeAdocoes.add(adocao);
        }
        BDDMockito.given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        assertThrows(ValidacaoException.class,() ->validador.validar(dto));

    }

    @Test
    void devePermitirSolicitacaoDeAdocaoTutorNaoAtingiuLimite5Adocoes(){

        adocao = new Adocao(tutor, pet, "Motivo qualquer");
        adocao.marcarComoAprovada();
        listaDeAdocoes.add(adocao);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(listaDeAdocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        assertDoesNotThrow(() ->validador.validar(dto));

    }

}