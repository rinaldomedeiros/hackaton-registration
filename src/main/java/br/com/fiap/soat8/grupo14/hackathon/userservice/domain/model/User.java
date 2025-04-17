package br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
  private Long id;
  private String username;
  private String email;
  private String password;
	  
}
