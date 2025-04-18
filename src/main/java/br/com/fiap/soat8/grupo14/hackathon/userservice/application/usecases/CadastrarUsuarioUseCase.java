package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioExistenteException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario execute(String username, String rawPassword) {
        validarUsuarioExistente(username);
        
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        Usuario user = new Usuario(null, username, encryptedPassword);
        
        return userRepository.save(user);
    }

    private void validarUsuarioExistente(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsuarioExistenteException(username);
        }
    }
}