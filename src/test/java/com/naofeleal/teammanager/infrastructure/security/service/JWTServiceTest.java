package com.naofeleal.teammanager.infrastructure.security.service;

import com.naofeleal.teammanager.core.application.repository.IJWTService;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
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
    private DBUser _dbUser;

    @BeforeEach
    void setUp() {
        _secretKey = "secretNotSoSecretsecretNotSoSecretsecretNotSoSecret";
        _jwtService = new JWTService(
                1000L,
                _secretKey,
                _userMapper
        );
        _user = new SimpleUser(
            new Name("Nao"),
            new Name("Fel"),
            new Email("test@example.com"),
            new Password("insecure_password")
        );
        _dbUser = new DBUser(
                "Nao",
                "Fel",
                "test@example.com",
                "insecure_password",
                RoleCode.SIMPLE_USER.toString()
        );
        when(_userMapper.fromDomainModel(any(BaseUser.class))).thenReturn(_dbUser);
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
        UserDetails userDetails = _dbUser;
        String token = _jwtService.generateToken(_user);

        assertTrue(_jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValidShouldReturnFalseForInvalidToken() {
        UserDetails userDetails = _dbUser;
        String validToken = _jwtService.generateToken(_user);
        String invalidToken = validToken.substring(0, validToken.length() - 1) + "x";

        assertFalse(_jwtService.isTokenValid(invalidToken, userDetails));
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