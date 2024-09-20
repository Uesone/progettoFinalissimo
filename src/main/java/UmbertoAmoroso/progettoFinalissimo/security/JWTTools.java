package UmbertoAmoroso.progettoFinalissimo.security;

import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import UmbertoAmoroso.progettoFinalissimo.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    // Genera un token per l'utente
    public String createToken(Utente utente) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 giorno di validità
                .setSubject(utente.getUsername()) // Soggetto, ovvero lo username dell'utente
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firma il token con un algoritmo e il segreto
                .compact();
    }

    // Verifica se il token è valido (non manipolato e non scaduto)
    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Token non valido o scaduto!");
        }
    }

    // Estrai lo username dal token
    public String extractUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Il subject è lo username
    }
}
