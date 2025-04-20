package br.com.fiap.soat8.grupo14.hackathon.userservice.unit;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRegistrationUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private CadastrarUsuarioUseCase cadastroUseCase;
    
    @Test
    void registerUser_ShouldSaveUserWithEncryptedPassword() {
        String rawPassword = "password123";
        String encodedPassword = "encoded123";
        Usuario expectedUser = new Usuario(null, "user", encodedPassword);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(expectedUser);

        Usuario result = cadastroUseCase.execute("user", rawPassword);
        
        assertEquals(encodedPassword, result.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }
}