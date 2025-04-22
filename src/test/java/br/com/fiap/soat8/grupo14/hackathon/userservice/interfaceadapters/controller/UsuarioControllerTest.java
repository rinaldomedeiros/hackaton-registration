package br.com.fiap.soat8.grupo14.hackathon.userservice.interfaceadapters.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ConsultarUsuarioPorUsernamelUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ListarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @MockBean
    private ConsultarUsuarioPorUsernamelUseCase consultarUsuarioPorUsernamelUseCase;

    @MockBean
    private ListarUsuarioUseCase listarUsuarioUseCase;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        when(jwtUtil.validateToken(any(String.class))).thenReturn(true);
        when(jwtUtil.extractUsername(any(String.class))).thenReturn("user");
    }

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

    @Test
    void deveListarUsuariosDeveRetornar200() throws Exception {
        when(listarUsuarioUseCase.execute())
            .thenReturn(List.of(new Usuario(1L, "user1", "encoded"), new Usuario(2L, "user2", "encoded")));

        mockMvc.perform(get("/usuarios")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    void deveListarUsuariosSemAutorizaçãoDeveRetornar403() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    void deveConsultarOUsuarioDeveRetornar200() throws Exception {
        when(consultarUsuarioPorUsernamelUseCase.execute("user"))
            .thenReturn(new Usuario(1L, "user", "encoded"));

        mockMvc.perform(get("/usuarios/consultar/user")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void deveConsultarUsuarioSemAutorizaçãoDeveRetornar403() throws Exception {
        mockMvc.perform(get("/usuarios/consultar/user"))
                .andExpect(status().isForbidden());
    }
}