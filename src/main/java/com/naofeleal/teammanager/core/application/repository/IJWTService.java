package com.naofeleal.teammanager.core.application.repository;


import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJWTService {
    String generateToken(BaseUser user);
    String generateToken(Map<String, Object> extraClaims, BaseUser user);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUserEmail(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}