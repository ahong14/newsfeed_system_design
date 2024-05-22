package com.newsfeed_system_design.newsfeed.services;

import com.newsfeed_system_design.newsfeed.models.User;
import com.newsfeed_system_design.newsfeed.repositories.UserRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;


// reference: https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    @Value("${jwt.expiration_time}")
    private long tokenDuration;

    @Value("${jwt.secret}")
    private String secret_key;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generateToken(User user) {
        Claims claims = Jwts
                .claims()
                .subject(user.getEmail())
                .add("firstName", user.getFirstName())
                .add("lastName", user.getLastName())
                .build();
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + tokenDuration);
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    private byte[] getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return keyBytes;
    }

    @Override
    public String loginUser(String email, String password) {
        Optional<User> foundUser = this.userRepository.findUserByEmail(email);
        if (foundUser.isEmpty()) {
            throw new NoSuchElementException("User not found with email.");
        }

        User foundUserObject = foundUser.get();

        if (!passwordEncoder.matches(password, foundUserObject.getPassword())) {
            throw new IllegalArgumentException("Invalid password provided");
        }

        String authToken =  generateToken(foundUserObject);
        return authToken;
    }

    private Claims extractAllClaims(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Date extractExpiration(String token) {
        Claims tokenClaims = extractAllClaims(token);
        return tokenClaims.getExpiration();
    }
}
