package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UserRepository;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.entity.UserEntity;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.mapper.UserMapper;


@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository jpaRepository;

    public JpaUserRepository(SpringDataJpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return UserMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

	@Override
	public Optional<User> findByUsername(String username) {
		return jpaRepository.findByUsername(username)
				.map(UserMapper::toDomain);
	}

	@Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
}
