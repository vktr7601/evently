package com.company.ticket_service.core.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private String secretKey = "dasdasdsadsadsajdbsakdbak";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                //who is the token about
                .subject(username)
                //when token was created
                .issuedAt(new Date(System.currentTimeMillis()))
                //set when the token will expire
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    // Single point of entry for parsing to save CPU cycles
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String usernameFromUserDetails) {
        final Claims claims = extractAllClaims(token);
        final String username = claims.getSubject();
        final boolean isExpired = claims.getExpiration().before(new Date());

        return (username.equals(usernameFromUserDetails) && !isExpired);
    }
}