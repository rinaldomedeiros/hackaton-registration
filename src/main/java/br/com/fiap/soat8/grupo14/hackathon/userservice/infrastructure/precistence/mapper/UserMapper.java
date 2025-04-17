package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.mapper;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.User;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.precistence.entity.UserEntity;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;

public class UserMapper {

	public static UserEntity toEntity(User user) {
	  UserEntity entity = new UserEntity();
	  entity.setId(user.getId());
	  entity.setUsername(user.getUsername());
	  entity.setEmail(user.getEmail());
	  entity.setPassword(user.getPassword());
	  return entity;
	}

	public static User toDomain(UserEntity entity) {
	  return new User(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword());
	}

	public static UserResponseDTO toResponse(User user) {
	  return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());

	}
}