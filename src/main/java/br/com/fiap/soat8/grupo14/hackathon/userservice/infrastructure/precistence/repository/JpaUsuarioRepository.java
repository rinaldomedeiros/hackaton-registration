package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.entity.UsuarioEntity;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.mapper.UsuarioMapper;


@Repository
public class JpaUsuarioRepository implements UsuarioRepository {

    private final SpringDataJpaUsuarioRepository jpaRepository;

    public JpaUsuarioRepository(SpringDataJpaUsuarioRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario save(Usuario user) {
        UsuarioEntity entity = UsuarioMapper.toEntity(user);
        UsuarioEntity savedEntity = jpaRepository.save(entity);
        return UsuarioMapper.toDomain(savedEntity);
    }

	@Override
	public Optional<Usuario> findByUsername(String username) {
		return jpaRepository.findByUsername(username)
				.map(UsuarioMapper::toDomain);
	}

	@Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
                .map(UsuarioMapper::toDomain)
                .collect(Collectors.toList());
    }
}
