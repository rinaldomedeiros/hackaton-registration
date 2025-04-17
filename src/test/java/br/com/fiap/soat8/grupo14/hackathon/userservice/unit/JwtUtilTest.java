package br.com.fiap.soat8.grupo14.hackathon.userservice.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat8.grupo14.hackathon.userservice.infrastructure.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;
    
    private final String SECRET = "z8kL7#pX$5vN@qR9!tB2mY4wG6jD1hF3eC5sJ7uK9xM0lO4iP8vQ2aZ5rT6yU";
    private final long EXPIRATION = 86400000L;
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtUtil, "expiration", EXPIRATION);
    }
    
    @Test
    void generateToken_ShouldReturnValidToken() {
        String token = jwtUtil.generateToken("user@test.com");
        
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);
    }
    
    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        String token = jwtUtil.generateToken("user@test.com");
        assertTrue(jwtUtil.validateToken(token));
    }
    
    @Test
    void validateToken_ShouldThrowForExpiredToken() {
        String oldToken = Jwts.builder()
                .setSubject("user@test.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - 100000))
                .setExpiration(new Date(System.currentTimeMillis() - 50000))
                .signWith(jwtUtil.getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
                
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(oldToken));
    }
}
