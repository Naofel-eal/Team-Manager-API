package com.naofeleal.teammanager.infrastructure.security.service;

import com.naofeleal.teammanager.core.application.repository.IJWTService;
import com.naofeleal.teammanager.core.domain.model.user.User;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.account.DBUser;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService implements IJWTService {
    private final Long _tokenValidationDurationInMs;
    private final Key _secretKey;
    private final IDBUserMapper _userMapper;

    public JWTService(
        @Value("${jwt.duration}") Long tokenValidationDurationInMs,
        @Value("${jwt.key}") String secretKey,
        final IDBUserMapper userMapper
    ) {
        this._tokenValidationDurationInMs = tokenValidationDurationInMs;
        this._secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        _userMapper = userMapper;
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        User user
    ) {
        DBUser userDetails = _userMapper.fromDomainModel(user);
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + _tokenValidationDurationInMs))
            .signWith(_secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String userEmail = extractUserEmail(token);
            return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SignatureException ex) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(_secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

