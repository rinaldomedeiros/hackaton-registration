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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CadastrarUsuarioUseCase cadastroUseCase;
    
    @Test
    void registerUser_ShouldReturn201() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "user", "user@test.com");
        when(cadastroUseCase.registerUser(any(), any(), any()))
            .thenReturn(new User(1L, "user", "user@test.com", "encoded"));
        
        mockMvc.perform(post("/usuarios/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "user",
                        "email": "user@test.com",
                        "password": "password123"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user"));
    }
}