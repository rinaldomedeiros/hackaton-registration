package br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository;

import java.util.Optional;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;

public interface UsuarioDomainRepository {
  
  Usuario save(Usuario user);
  Optional<Usuario> findByUsername(String username);
}