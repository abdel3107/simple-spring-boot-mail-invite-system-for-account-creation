package com.example.mailing.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {


    private String secretKey = "5qAq6zGyX8bC3dV2wS7aN1mK9jF0hL9sUoP6iBvE9nG8xZaQaY7cW2fA";
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    private long expiration = 3600000 ; // 1 hour in millisecond
    private long expiration = 60000; // 1min in milliseconds

    public String generateToken(String email) {
        Key Key ;
        return Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
//        signWith(SignatureAlgorithm.HS512, secretKey)
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String expectedEmail) {
        Claims claims = extractClaims(token);
        String email = claims.get("email", String.class);
        return email.equals(expectedEmail) && !isTokenExpired(token) && isTokenExpired(token);
    }

}
