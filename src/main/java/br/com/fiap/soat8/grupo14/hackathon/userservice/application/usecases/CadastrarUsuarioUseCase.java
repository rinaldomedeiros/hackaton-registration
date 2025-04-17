package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.EmailAlreadyExistsException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsernameAlreadyExistsException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UserRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String rawPassword) {
        validateUserDoesNotExist(email, username);
        
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(null, username, email, encryptedPassword);
        
        return userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validateUserDoesNotExist(String email, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
    }
}