package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {


        @InjectMocks
        private AdocaoService service;

        @Mock
        private AdocaoRepository repository;

        @Mock
        private PetRepository petRepository;

        @Mock
        private TutorRepository tutorRepository;

        @Mock
        private EmailService emailService;

        @Mock
        private List<ValidacaoSolicitacaoAdocao> validacoes;

        @Mock
        private Pet pet;

        @Mock
        private Tutor tutor;

        @Mock
        private Abrigo abrigo;

        @Captor
        private ArgumentCaptor<Adocao> adocaoCaptor;

        private SolicitacaoAdocaoDto dto;


        @Test
        void deveriaSalvarAdocaoAoSolicitar() {

             //arrange
              this.dto = new SolicitacaoAdocaoDto(10l,20l, "motivo qualquer");
              given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
              given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
              given(pet.getAbrigo()).willReturn(abrigo);

             //act
            service.solicitar(dto);

            //assert
            then(repository).should().save(adocaoCaptor.capture());
            Adocao adocaoSalva = adocaoCaptor.getValue();
            assertEquals(pet,adocaoSalva.getPet());
            assertEquals(tutor,adocaoSalva.getTutor());
            assertEquals(dto.motivo(), adocaoSalva.getMotivo());
        }

}