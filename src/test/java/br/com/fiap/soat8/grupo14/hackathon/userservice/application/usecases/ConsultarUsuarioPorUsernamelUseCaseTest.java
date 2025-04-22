package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;

class ConsultarUsuarioPorUsernamelUseCaseTest {

    @Mock
    private UsuarioRepository userRepository;

    @InjectMocks
    private ConsultarUsuarioPorUsernamelUseCase useCase;

    public ConsultarUsuarioPorUsernamelUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoUsernameExistir() {
        String username = "user";
        Usuario usuario = new Usuario(1L, username, "password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(usuario));

        Usuario result = useCase.execute(username);

        assertEquals(usuario, result);
    }

    @Test
    void deveLancarExcecaoQuandoUsernameNaoExistir() {
        String username = "user";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.execute(username));
    }
}