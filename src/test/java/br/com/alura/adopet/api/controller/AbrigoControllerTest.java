package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private AbrigoService abrigoService;

    @Mock
    private PetService petService;

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
    void deveRetornarListaDeAbrigos() throws Exception {

        var response = mvc.perform(
                    get("/abrigos")
                            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }
}