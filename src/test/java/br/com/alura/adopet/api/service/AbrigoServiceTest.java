package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {


    @InjectMocks
    private AbrigoService service;

    @Mock
    private Abrigo abrigo;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    private CadastroAbrigoDto dto;

    @Test
    void deveCadastrarAbrigo(){
        this.dto = new CadastroAbrigoDto("Abrigo novo", "(31)2222-2222","teste@gmail.com");
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(false);

        service.cadatrar(dto);

        then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigoSalvo = abrigoCaptor.getValue();
        assertEquals(dto.nome(),abrigoSalvo.getNome());
        assertEquals(dto.telefone(),abrigoSalvo.getTelefone());
        assertEquals(dto.email(),abrigoSalvo.getEmail());
    }

    @Test
    void deveRetornarComoAbrigoJaCadastrado(){
        this.dto = new CadastroAbrigoDto("Abrigo novo", "(31)2222-2222","teste@gmail.com");
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> service.cadatrar(dto));
    }

    @Test
    void deveCarregarAbrigoComId(){
        given(abrigoRepository.findById(1l)).willReturn(Optional.of(abrigo));

        assertDoesNotThrow(() -> service.carregarAbrigo("1"));
    }

    @Test
    void deveNaoCarregarAbrigoComId(){
        Optional<Abrigo> optionalVazio = Optional.empty();
        given(abrigoRepository.findById(1l)).willReturn(optionalVazio);

        assertThrows(ValidacaoException.class,() -> service.carregarAbrigo("1"));
    }

    @Test
    void deveCarregarAbrigoComNome(){
        given(abrigoRepository.findByNome("Abrigo novo")).willReturn(Optional.of(abrigo));

        assertDoesNotThrow(() -> service.carregarAbrigo("Abrigo novo"));
    }

    @Test
    void deveChamarListaDeTodosOsAbrigos(){
        service.listar();

        then(abrigoRepository).should().findAll();
    }

    @Test
    void deveChamarListaDePetsDoAbrigoAtravesDoNome(){
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        service.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }

}