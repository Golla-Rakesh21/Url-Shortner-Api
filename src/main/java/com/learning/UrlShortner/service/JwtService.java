package com.learning.UrlShortner.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {


    public String generateSecret() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String Secret = Base64.getEncoder().encodeToString(bytes);
        return Secret;
    }
    private final SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(generateSecret()));
    private final long expirationMs=900000;

    public String generateToken(String email){
        Date now=new Date();
        Date expiry=new Date(now.getTime()+expirationMs);


        return Jwts.builder().subject(email).issuedAt(now).expiration(expiry)
                .signWith(key).compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
    }

    public String extractEmail(String token){
        return parseToken(token).getSubject();
    }

}
