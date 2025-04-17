package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.entity.UserEntity;

@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}
