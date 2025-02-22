package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @MockBean
    private Abrigo abrigo;

    @Test
    void deveRetornarCodigo400ParaCadastroDeAbrigo() throws Exception {

        String json = "{}";

        var response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400,response.getStatus());
    }

    @Test
    void deveRetornarCodigo200ParaCadastroDeAbrigo() throws Exception {

        String json = """
                    {
                        "nome": "Abrigo novo",
                        "telefone": "(31)6666-7777",
                        "email": "abrigo@gmail.com"
                    }
                """;

        var response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    void deveRetornar200ListaDeAbrigos() throws Exception {

        var response = mvc.perform(
                    get("/abrigos")
                            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200ListaDeAbrigosPorNome() throws Exception {

        String nome = "Abrigo Feliz";

        var response = mvc.perform(
                get("/abrigos/{nome}/pets",nome)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    void deveRetornar200ListaDeAbrigosPorId() throws Exception {

        String id = "1";

        var response = mvc.perform(
                get("/abrigos/{id}/pets",id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());
    }

    @Test
    void deveRetornar400ListaDeAbrigosPorNomeInvalido() throws Exception {

        String nome = "Miau";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                get("/abrigos/{nome}/pets",nome)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404,response.getStatus());
    }

    @Test
    void deveRetornar400ListaDeAbrigosPorIdInvalido() throws Exception {

        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                get("/abrigos/{id}/pets",id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404,response.getStatus());
    }

    @Test
    void deveRetornar200CadastroDePetPeloId() throws Exception {

        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        var response = mvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());

    }

    @Test
    void deveRetornar200CadastroDePetPeloNome() throws Exception {

        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String nome = "Abrigo Feliz";

        var response = mvc.perform(
                post("/abrigos/{nome}/pets", nome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200,response.getStatus());

    }

    @Test
    void deveRetornar404CadastroDePetPeloIdInvalido() throws Exception {

        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        given(abrigoService.carregarAbrigo(abrigoId)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                post("/abrigos/{abrigoId}/pets", abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404,response.getStatus());

    }

    @Test
    void deveRetornar404CadastroDePetPeloNomeInvalido() throws Exception {

        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String nome = "Abrigo Errado";

        given(abrigoService.carregarAbrigo(nome)).willThrow(ValidacaoException.class);

        var response = mvc.perform(
                post("/abrigos/{nome}/pets", nome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404,response.getStatus());

    }

}