package com.mgunawardhana.microservices.spring.service.impl;

import com.mgunawardhana.microservices.spring.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @NotNull
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @NotNull
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @NotNull
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        T claimResolverValue = null;
        try {
            final Claims claims = extractAllClaims(token);
            claimResolverValue =claimsResolver.apply(claims);
        }catch (ExpiredJwtException e) {
            log.error("Access Token Expired Exception: {}", e.getMessage().toUpperCase());
        }
        return claimResolverValue;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extractClaims, UserDetails userDetails, long expiration) {
        String buildToken = null;
        try{
            buildToken = Jwts.builder().setClaims(extractClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
        }catch(JwtException e){
            log.error("Error Occurred While Building Token");
        }
        return Objects.requireNonNull(buildToken,"This Token is Null!");
    }

    @Override
    public boolean isTokenValidated(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] secretBytes = Decoders.BASE64.decode(Objects.requireNonNull(secretKey));
        return Keys.hmacShaKeyFor(secretBytes);
    }
}