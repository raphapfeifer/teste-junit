package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    private CadastroTutorDto dto;

    @Captor
    private ArgumentCaptor<Tutor> tutorCaptor;

    @Test
    void deveCadastrarTutor(){
        this.dto = new CadastroTutorDto("Zé Colméia", "(11)6784-2190", "qualquer@gmail.com");
        given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(false);

        service.cadastrar(dto);

        then(repository).should().save(tutorCaptor.capture());
        Tutor tutorSalvo = tutorCaptor.getValue();
        assertEquals(dto.nome(), tutorSalvo.getNome());
        assertEquals(dto.telefone(),tutorSalvo.getTelefone());
        assertEquals(dto.email(),tutorSalvo.getEmail());
    }

    @Test
    void deveRetornarTutorCadastrado(){
        this.dto = new CadastroTutorDto("Zé Colméia", "(11)6784-2190", "qualquer@gmail.com");
        given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
    }

}