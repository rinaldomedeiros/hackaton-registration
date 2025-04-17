package br.com.fiap.soat8.grupo14.hackathon.userservice.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserRegistrationUseCaseTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private CadastrarUsuarioUseCase cadastroUseCase;
    
    @Test
    void registerUser_ShouldSaveUserWithEncryptedPassword() {
        String rawPassword = "password123";
        String encodedPassword = "encoded123";
        User expectedUser = new User(null, "user", "user@test.com", encodedPassword);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        
        User result = cadastroUseCase.registerUser("user", "user@test.com", rawPassword);
        
        assertEquals(encodedPassword, result.getPassword());
        verify(userRepository).save(any(User.class));
    }
}