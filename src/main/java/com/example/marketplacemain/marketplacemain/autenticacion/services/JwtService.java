package com.example.marketplacemain.marketplacemain.autenticacion.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import static com.example.marketplacemain.marketplacemain.autenticacion.security.TokenJWT.SECRET_KEY;

@Service
public class JwtService {

    // Generate a secure key using HMAC-SHA256
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    
    // Get username from token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
        String usename = claims.getSubject();
        return usename;
    }
    
    // Get expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    // Extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    // Check if token is expired
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    // Validate token
    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method to decode JWT without verification (useful for debugging)
    public Claims decodeJwtWithoutVerification(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        
        String payload = new String(decoder.decode(chunks[1]));
        return Jwts.parser()
                .build()
                .parseClaimsJwt(chunks[0] + "." + chunks[1] + ".")
                .getPayload();
    }

    // Método para obtener la SecretKey (si necesitas acceder a ella)
    public SecretKey getSecretKey() {
        return secretKey;
    }
}
