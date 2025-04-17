package br.com.fiap.soat8.grupo14.hackathon.userservice.interfaceadapters.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.AutenticarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ConsultarUsuarioPorEmailUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ListarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.mapper.UserMapper;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationResponseDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UserController {

  private final CadastrarUsuarioUseCase registerUserUseCase;
  private final ConsultarUsuarioPorEmailUseCase consultarUsuarioPorEmailUseCase;
  private final ListarUsuarioUseCase listarUsuarioUseCase;
  private final AutenticarUsuarioUseCase authenticateUserUseCase;

  @PostMapping("/autenticar")
  public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
      AuthenticationResponseDTO response = authenticateUserUseCase.execute(request);
      return ResponseEntity.ok(response);
  }
  
  @PostMapping("/cadastrar")
  public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
    User user = registerUserUseCase.registerUser(dto.username, dto.email, dto.password);
    UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    return ResponseEntity.ok(response);
  }
  
  @GetMapping("/email/{email}")
  public ResponseEntity<UserResponseDTO> consultarPorEmail(@PathVariable String email) {
	    try {
	        User user = consultarUsuarioPorEmailUseCase.execute(email);
	        return ResponseEntity.ok(UserMapper.toResponse(user));
	    } catch (UsuarioNaoEncontradoException ex) {
	        return ResponseEntity.notFound().build();
	    }
	}
  
  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
      List<User> users = listarUsuarioUseCase.getAllUsers();
      List<UserResponseDTO> response = users.stream()
              .map(UserMapper::toResponse)
              .collect(Collectors.toList());
      return ResponseEntity.ok(response);
  }
  
}
