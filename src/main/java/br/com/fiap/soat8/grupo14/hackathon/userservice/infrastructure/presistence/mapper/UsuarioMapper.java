package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.presistence.mapper;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.presistence.entity.UsuarioEntity;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.UserResponseDTO;

public class UsuarioMapper {

	public static UsuarioEntity toEntity(Usuario user) {
	  UsuarioEntity entity = new UsuarioEntity();
	  entity.setId(user.getId());
	  entity.setUsername(user.getUsername());
	  entity.setPassword(user.getPassword());
	  return entity;
	}

	public static Usuario toDomain(UsuarioEntity entity) {
	  return new Usuario(entity.getId(), entity.getUsername(), entity.getPassword());
	}

	public static UserResponseDTO toResponse(Usuario user) {
	  return new UserResponseDTO(user.getId(), user.getUsername());

	}
}