package br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository;

import java.util.Optional;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;

public interface UserDomainRepository {
  
  User save(User user);
  Optional<User> findByEmail(String email);
}