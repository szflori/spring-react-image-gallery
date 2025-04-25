package com.example.api.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.api.modules.auth.model.AuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expiration-hours}")
    private long expiration;

    public static final int HOUR = 60 * 60 * 1000;
    public static final int DAY = HOUR * 24;

    public Map<String, Object> createClaims(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", authUser.getUsername());
        claims.put("email", authUser.getEmail());
        claims.put("roles", authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray());
        return claims;
    }

    public String generateToken(AuthUser authUser, long expirationTime) {
        Map<String, Object> claims = createClaims(authUser);
        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authUser.getUsername())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(AuthUser authUser) {
        return generateToken(authUser, DAY * expiration);
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        }
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
