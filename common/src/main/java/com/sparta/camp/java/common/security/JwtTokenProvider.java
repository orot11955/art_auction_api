package com.sparta.camp.java.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access-token-validity:3600}")
  private long accessTokenValidity;

  @Value("${jwt.refresh-token-validity:86400}")
  private long refreshTokenValidity;

  private SecretKey getSigningKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(Long userId, String username, String role) {
    return generateToken(userId, username, role, accessTokenValidity);
  }

  public String generateRefreshToken(Long userId, String username) {
    return generateToken(userId, username, "REFRESH", refreshTokenValidity);
  }

  private String generateToken(Long userId, String username, String role, long validity) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + validity * 1000);

    return Jwts.builder()
        .setSubject(username)
        .claim("userId", userId)
        .claim("role", role)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      return false;
    }
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.get("userId", Long.class);
  }

  public String getRoleFromToken(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.get("role", String.class);
  }

  public boolean isTokenExpired(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody();

      return claims.getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return true;
    }
  }
}

