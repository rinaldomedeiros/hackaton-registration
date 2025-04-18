package br.com.fiap.soat8.grupo14.hackathon.userservice.interfaceadapters.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.AutenticarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.CadastrarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ConsultarUsuarioPorUsernamelUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases.ListarUsuarioUseCase;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.mapper.UsuarioMapper;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.AuthenticationResponseDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserRequestDTO;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Serviço para cadastro de usuários")
public class UsuarioController {

  private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
  private final ConsultarUsuarioPorUsernamelUseCase consultarUsuarioPorUsernamelUseCase;
  private final ListarUsuarioUseCase listarUsuarioUseCase;
  private final AutenticarUsuarioUseCase authenticateUserUseCase;

  @PostMapping("/autenticar")
  @Operation(summary = "Este endpoint é responsável por autenticar e gerar o token.")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
	  AuthenticationResponseDTO response = authenticateUserUseCase.execute(request);
	  return ResponseEntity.ok(response);
  }
  
  @PostMapping("/cadastrar")
  @Operation(summary = "Este endpoint é responsável por cadastrar um usuário.")
  public ResponseEntity<?> register(@RequestBody UserRequestDTO dto) {
	  Usuario user = cadastrarUsuarioUseCase.execute(dto.username, dto.password);
	  UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername());
	  return ResponseEntity.ok(response);
  }
  
  @GetMapping
  @Operation(summary = "Este endpoint é responsável por listar todos os usuários.")
  public ResponseEntity<?> consultarUsuarios() {
	  List<Usuario> users = listarUsuarioUseCase.execute();
	  List<UserResponseDTO> response = users.stream()
			  .map(UsuarioMapper::toResponse)
			  .collect(Collectors.toList());
	  return ResponseEntity.ok(response);
  }
  
  @GetMapping("/consultar/{username}")
  @Operation(summary = "Este endpoint é responsável por consultar um usuário.")
  public ResponseEntity<?> consultarUsuario(@RequestParam String username) {
	  Usuario user = consultarUsuarioPorUsernamelUseCase.execute(username);
	  UserResponseDTO response = UsuarioMapper.toResponse(user);
	  return ResponseEntity.ok(response);
  }

}
