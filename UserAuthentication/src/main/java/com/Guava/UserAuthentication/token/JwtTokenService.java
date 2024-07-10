package com.Guava.UserAuthentication.token;

import com.Guava.UserAuthentication.user.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

@Service
public class JwtTokenService {

    private String SECRET_KEY = "33743677397A24432646294A404E635266556A576E5A7234753778214125442A";
    private Date CURRENT_TIME = new Date(System.currentTimeMillis());
    private Date EXPIRATION_TIME = new Date(System.currentTimeMillis()+ 1000*60*60*24); // one day


    public String genereteToken(CustomUserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        return true;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return null;
    }

    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(CURRENT_TIME)
                .setExpiration(EXPIRATION_TIME)
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
