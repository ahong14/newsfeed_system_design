package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiration_time}")
    private long tokenDuration;

    @Value("${jwt.secret}")
    private String secretKey;


    /**
     *
     * @param user - user payload building JWT for
     * @return JWT string
     */
    @Override
    public String generateToken(User user) {
        Claims claims = Jwts
                .claims()
                .subject(user.getEmail())
                .add("firstName", user.getFirstName())
                .add("lastName", user.getLastName())
                .build();
        // setup token issued at time, current time in milliseconds
        Date tokenCreateTime = new Date();
        // setup token expiration time, add 1 hour in milliseconds to current time
        Date tokenExpiration = new Date(System.currentTimeMillis() + tokenDuration);
        SecretKey signingKey = getSigningKey();
        // build JWT
        return Jwts.builder()
                .claims(claims)
                .issuedAt(tokenCreateTime)
                .expiration(tokenExpiration)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param token - JWT token as String
     * @return - claims within decoded token
     */
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload();
    }


    /**
     *
     * @param token - JWT token as String
     * @return - expiration date of JWT
     */
    @Override
    public Date extractExpiration(String token) {
        Claims tokenClaims = extractAllClaims(token);
        return tokenClaims.getExpiration();
    }

    /**
     *
     * @param token - JWT token as String
     * @return - email/subject of decoded JWT
     */
    @Override
    public String extractEmail(String token) {
        Claims tokenClaims = extractAllClaims(token);
        return tokenClaims.getSubject();
    }

    /**
     *
     * @param token - JWT token as String
     * @return - boolean indicating if JWT is expired based on expiration date
     */
    @Override
    public Boolean isTokenExpired(String token) {
        Claims tokenClaims = extractAllClaims(token);
        return tokenClaims.getExpiration().before(new Date());
    }


    /**
     *
     * @param token - JWT token as String
     * @return - boolean indicating if JWT is valid based on expiration date and email matches user details
     */
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
