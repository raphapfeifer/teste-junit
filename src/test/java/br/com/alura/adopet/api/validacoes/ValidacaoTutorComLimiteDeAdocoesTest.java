package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private PetRepository petRepository;

    @Spy
    private List<Adocao> adocoes = new ArrayList<>();

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacaoTutorComLimiteDeAdocoes;

    @Test
    void deveRetornarTutorComLimiteDeAdocoes(){

        this.dto = new SolicitacaoAdocaoDto(10l,20l, "motivo qualquer");
        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        Adocao adocao1 = new Adocao(tutor, pet, dto.motivo());
        adocao1.marcarComoAprovada();
        Adocao adocao2 = new Adocao(tutor, pet, dto.motivo());
        adocao2.marcarComoAprovada();
        Adocao adocao3 = new Adocao(tutor, pet, dto.motivo());
        adocao3.marcarComoAprovada();
        Adocao adocao4 = new Adocao(tutor, pet, dto.motivo());
        adocao4.marcarComoAprovada();
        Adocao adocao5 = new Adocao(tutor, pet, dto.motivo());
        adocao5.marcarComoAprovada();

        adocoes.add(adocao1);
        adocoes.add(adocao2);
        adocoes.add(adocao3);
        adocoes.add(adocao4);
        adocoes.add(adocao5);

        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComLimiteDeAdocoes.validar(this.dto));

    }

}