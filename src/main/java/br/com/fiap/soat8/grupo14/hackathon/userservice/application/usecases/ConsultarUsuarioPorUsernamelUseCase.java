package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import org.springframework.stereotype.Service;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ConsultarUsuarioPorUsernamelUseCase {
    private final UsuarioRepository userRepository;

    public Usuario execute(String username) {
    	return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(username));
    }
}
