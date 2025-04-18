package br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;

public interface UsuarioRepository {
  Usuario save(Usuario user);
  Optional<Usuario> findByUsername(String username);
  List<Usuario> findAll();
}
