package com.naofeleal.teammanager.infrastructure.security.service;

import com.naofeleal.teammanager.core.application.exception.authentication.InvalidTokenException;
import com.naofeleal.teammanager.core.application.repository.IJWTService;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBTeamMapper;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBSimpleUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {
    private IJWTService _jwtService;

    @Mock
    private IDBUserMapper _userMapper;

    private String _secretKey;
    private BaseUser _user;
    private DBSimpleUser _dbSimpleUser;

    @BeforeEach
    void setUp() {
        _secretKey = "secretNotSoSecretsecretNotSoSecretsecretNotSoSecret";
        _jwtService = new JWTService(
                10000L,
                _secretKey,
                _userMapper
        );
        _user = new SimpleUser(
            new Name("Nao"),
            new Name("Fel"),
            new Email("test@example.com"),
            new Password("insecure_password")
        );
        _dbSimpleUser = new DBSimpleUser(
            "Nao",
            "Fel",
            "test@example.com",
            "insecure_password"
        );
        when(_userMapper.fromDomainModel(any(BaseUser.class))).thenReturn(_dbSimpleUser);
    }

    @Test
    void generateTokenShouldGenerateValidToken() {
        String token = _jwtService.generateToken(_user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals("test@example.com", _jwtService.extractUserEmail(token));
    }

    @Test
    void generateTokenWithExtraClaimsShouldGenerateTokenWithClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        String token = _jwtService.generateToken(extraClaims, _user);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims = Jwts.parserBuilder().setSigningKey(_secretKey).build().parseClaimsJws(token).getBody();
        assertEquals("ADMIN", claims.get("role"));
        assertEquals("test@example.com", claims.getSubject());
    }

    @Test
    void isTokenValidShouldReturnTrueForValidToken() {
        UserDetails userDetails = _dbSimpleUser;
        String token = _jwtService.generateToken(_user);

        assertTrue(_jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValidShouldThrowInvalidTokenException() {
        UserDetails userDetails = _dbSimpleUser;
        String validToken = _jwtService.generateToken(_user);
        String invalidToken = validToken.substring(0, validToken.length() - 1) + "x";

        assertThrows(InvalidTokenException.class, () -> _jwtService.isTokenValid(invalidToken, userDetails));
    }

    @Test
    void extractUserEmailShouldExtractUserEmail() {
        String token = _jwtService.generateToken(_user);
        assertEquals("test@example.com", _jwtService.extractUserEmail(token));
    }

    @Test
    void extractClaimShouldReturnCorrectClaim() {
        String token = _jwtService.generateToken(_user);

        String extractedEmail = _jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("test@example.com", extractedEmail);
    }
}