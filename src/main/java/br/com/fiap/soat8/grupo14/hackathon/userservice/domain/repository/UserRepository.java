package br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;

public interface UserRepository {
  User save(User user);
  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
  List<User> findAll();
}
