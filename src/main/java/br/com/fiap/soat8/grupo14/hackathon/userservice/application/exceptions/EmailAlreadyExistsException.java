package br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1970063109022912064L;

	public EmailAlreadyExistsException(String email) {
        super("Email " + email + " já está em uso");
    }
}
