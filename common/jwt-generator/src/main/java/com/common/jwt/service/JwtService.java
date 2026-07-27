package com.common.jwt.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtService {

    private final Key key;
    private final long expirationTimeGlobal;

    public JwtService(String secret, long expirationTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTimeGlobal = expirationTime;
    }

    // 1. Generate Token
    public String generateToken(String username, Map<String, Object> extraClaims,Long expirationTime) {
    	Date issuedAt = new Date(System.currentTimeMillis());
    	Date expiration = new Date(System.currentTimeMillis() + expirationTime);

    	log.info("Issued At : {}", issuedAt);
    	log.info("Expiration : {}", expiration);
    	log.info("Expiration Time(ms): {}", expirationTime);
		/*
		 * long expirationTimeCalc = expirationTime * 60 * 1000L;
		 */
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }
    
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    // 2. Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Validate Token Structure and Expiration
    public boolean isTokenValid(String token, String expectedUsername) {
        try {
            final String username = extractUsername(token);
            return (username.equals(expectedUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false; // Signature corruption, tampering, or malformed tokens catch here
        }
    }

    // --- Private Helper Methods ---
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
