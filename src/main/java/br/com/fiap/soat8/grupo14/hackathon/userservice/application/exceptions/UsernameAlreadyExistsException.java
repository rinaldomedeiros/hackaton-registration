package br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 945620488538118472L;

	public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " já está em uso");
    }
}