package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.presistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.presistence.entity.UsuarioEntity;

@Repository
public interface SpringDataJpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
}
