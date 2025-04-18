package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarUsuarioUseCase {
    
    private final UsuarioRepository repository;
    
    public List<Usuario> execute() {
        return repository.findAll();
    }
}