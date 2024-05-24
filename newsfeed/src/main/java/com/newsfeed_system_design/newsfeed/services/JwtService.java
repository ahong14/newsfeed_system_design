package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JwtService {
    String generateToken(User user);

    String extractEmail(String token);

    Date extractExpiration(String token);

    SecretKey getSigningKey();

    Claims extractAllClaims(String token);

    Boolean isTokenValid(String token, UserDetails userDetails);

    Boolean isTokenExpired(String token);
}
