package br.com.fiap.soat8.grupo14.hackathon.userservice.interfaceadapters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.CredencialInvalidaException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.TokenExpiradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.TokenInvalidoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioExistenteException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.soat8.grupo14.hackathon.userservice.presentation.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredencialInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleCredencialInvalidaException(CredencialInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Erro: " + ex.getMessage()));
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioExistenteException(UsuarioExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Erro: " + ex.getMessage()));
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Erro: " + ex.getMessage()));
    }

    @ExceptionHandler({TokenExpiradoException.class, TokenInvalidoException.class})
    public ResponseEntity<ErrorResponseDTO> handleTokenExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO("Erro: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Erro interno: " + ex.getMessage()));
    }
}