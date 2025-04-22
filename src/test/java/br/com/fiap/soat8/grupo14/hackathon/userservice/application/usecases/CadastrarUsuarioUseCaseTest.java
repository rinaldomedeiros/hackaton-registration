package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioExistenteException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @Test
    void deveSalvarOUsuarioQuandoOUsuarioNaoExistir() {
        String username = "newUser";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = cadastrarUsuarioUseCase.execute(username, rawPassword);

        assertEquals(username, result.getUsername());
        assertEquals(encodedPassword, result.getPassword());
        verify(userRepository).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoOUsuarioJaExistir() {
        String username = "existingUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new Usuario(1L, username, "password")));

        assertThrows(UsuarioExistenteException.class, () -> cadastrarUsuarioUseCase.execute(username, "password123"));
        verify(userRepository, never()).save(any(Usuario.class));
    }
}