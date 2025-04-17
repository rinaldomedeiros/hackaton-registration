package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UserRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationResponseDTO execute(AuthenticationRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Credenciais inválidas"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsuarioNaoEncontradoException("Credenciais inválidas");
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthenticationResponseDTO(token);
    }
}
