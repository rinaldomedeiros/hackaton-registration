package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.CredencialInvalidaException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {
    
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationResponseDTO execute(AuthenticationRequestDTO request) {
        Usuario user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new CredencialInvalidaException());
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CredencialInvalidaException();
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthenticationResponseDTO(token);
    }
}
