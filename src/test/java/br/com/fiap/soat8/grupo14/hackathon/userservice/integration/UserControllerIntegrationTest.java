package br.com.fiap.soat8.grupo14.hackathon.userservice.integration;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private CadastrarUsuarioUseCase cadastroUseCase;
    
    @Test
    void registerUser_ShouldReturn201() throws Exception {
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "user");
        when(cadastroUseCase.execute(any(), any()))
            .thenReturn(new Usuario(1L, "user", "encoded"));
        
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