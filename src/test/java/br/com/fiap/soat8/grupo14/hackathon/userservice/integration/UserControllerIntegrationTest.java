package br.com.fiap.soat8.grupo14.hackathon.userservice.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    
    @Test
    void deveCadastrarUsuarioDeveRetornar200() throws Exception {
        when(cadastrarUsuarioUseCase.execute(any(), any()))
            .thenReturn(new Usuario(1L, "user", "encoded"));

        mockMvc.perform(post("/usuarios/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "user",
                        "password": "password123"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user"));
    }
}