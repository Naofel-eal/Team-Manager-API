package com.naofeleal.teammanager.core.application.repository;


import com.naofeleal.teammanager.core.domain.model.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJWTService {
    String generateToken(User user);
    String generateToken(Map<String, Object> extraClaims, User user);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUserEmail(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}