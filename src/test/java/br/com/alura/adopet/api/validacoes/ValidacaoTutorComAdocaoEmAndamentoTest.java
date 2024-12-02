package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private List<Adocao> adocoes;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @InjectMocks
    ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Test
    void deveRetornarTutorComAdocaoEmAndamento(){

        this.dto = new SolicitacaoAdocaoDto(10l,20l, "motivo qualquer");

        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);

        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);



        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));


    }


}