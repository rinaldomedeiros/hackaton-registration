package br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -1075533397280162154L;

	public UsuarioNaoEncontradoException(String username) {
        super("Usuário com username " + username + " não encontrado");
    }
}
