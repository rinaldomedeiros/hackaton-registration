package br.com.fiap.soat8.grupo14.hackathon.userservice.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.model.Usuario;
import br.com.fiap.soat8.grupo14.hackathon.userservice.domain.repository.UsuarioRepository;

class ListarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository userRepository;

    @InjectMocks
    private ListarUsuarioUseCase useCase;

    public ListarUsuarioUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
            new Usuario(1L, "user1", "password1"),
            new Usuario(2L, "user2", "password2")
        );
        when(userRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = useCase.execute();

        assertEquals(usuarios, result);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverUsuarios() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<Usuario> result = useCase.execute();

        assertEquals(0, result.size());
    }
}