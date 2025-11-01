package com.vmmedico.authentication.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenUtil {

    private static final String SECRET = "bXlzdXBlcnNlY3JldGtleTEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIz"; // long random base64 key
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private final Key key;

    public JwtTokenUtil() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }


    public String generateToken(Long userId, String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role.toUpperCase())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public Long getUserIdFromToken(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
