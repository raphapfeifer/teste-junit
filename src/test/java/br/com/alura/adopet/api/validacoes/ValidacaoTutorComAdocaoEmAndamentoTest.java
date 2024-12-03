package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Adocao adocao;

    @Spy
    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Tutor tutor;

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Test
    void deveRetornarTutorComAdocaoEmAndamento(){

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        this.dto = new SolicitacaoAdocaoDto(10l,20l, "motivo qualquer");

        this.adocao = new Adocao(tutor, pet, dto.motivo());

        adocoes.add(adocao);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void deveNaoRetornarTutorComAdocaoEmAndamento(){

        this.dto = new SolicitacaoAdocaoDto(10l,20l, "motivo qualquer");

        //BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);

        //BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);

        Assertions.assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }


}