package br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequestDTO {
  public String username;
  public String password;
}
