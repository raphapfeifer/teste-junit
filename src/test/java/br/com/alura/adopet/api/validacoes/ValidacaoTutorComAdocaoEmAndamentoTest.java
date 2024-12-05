package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void deveRetornarTutorComAdocaoEmAndamento(){

        given(adocaoRepository.existsByPetIdAndStatus(dto.idTutor(),StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);


        assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }

    @Test
    void devePermitirTutorSemAdocaoEmAndamento(){

        given(adocaoRepository.existsByPetIdAndStatus(dto.idTutor(),StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);


        assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(dto));
    }


}