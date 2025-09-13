package br.com.fiap.backend_Softtek.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
   @Value("${app.jwtSecret}")
   private String jwtSecret;

   @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

   private Key getSigningKey() {
       byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
       return Keys.hmacShaKeyFor(keyBytes);
   }

   public String generateToken(String username) {
       return Jwts.builder()
               .subject(username)
               .issuedAt(new Date())
               .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
               .signWith(this.getSigningKey(), SignatureAlgorithm.HS512)
               .compact();
   }

   public String getUserIdFromToken(String token) {
       Claims claims = Jwts.parser()
               .verifyWith((javax.crypto.SecretKey) this.getSigningKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();

       return claims.getSubject();
   }

   public boolean validateToken(String authToken) {
       try {
           Jwts.parser()
                   .verifyWith((javax.crypto.SecretKey) this.getSigningKey())
                   .build()
                   .parseSignedClaims(authToken);
           return true;
       } catch (Exception e) {
           System.out.println("Erro de validação do token... " + e.getMessage());
       }
       return false;
   }
}