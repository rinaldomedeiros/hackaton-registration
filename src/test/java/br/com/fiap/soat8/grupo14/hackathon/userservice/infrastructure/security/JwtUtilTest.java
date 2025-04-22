package br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat8.grupo14.hackathon.userservice.application.exceptions.TokenExpiradoException;

class JwtUtilTest {
	  
	@Spy
    private JwtUtil jwtUtil;

    private final String SECRET = "testSecret12345678901234567890123456789012"; 
    private final long EXPIRATION = 1000L; // 1 segundo para teste

    @BeforeEach
    void setUp() {
    	jwtUtil = spy(new JwtUtil());
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", EXPIRATION);
    }

    @Test
    void deveRetornarTokenValido() {
        String username = "usertest";
        String token = jwtUtil.generateToken(username);

        assertEquals(username, jwtUtil.extractUsername(token));
    }

    @Test
    void deveGerarExcecaoQuandoOTokenExpirar() {
        Date now = new Date();
        doReturn(now).when(jwtUtil).getCurrentTime();

        String token = jwtUtil.generateToken("user");

        doReturn(new Date(now.getTime() + EXPIRATION + 1)).when(jwtUtil).getCurrentTime();

        assertThrows(TokenExpiradoException.class, () -> jwtUtil.validateToken(token));
    }
}