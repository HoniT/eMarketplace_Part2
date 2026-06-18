package ge.mziuri.emarket.service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final SecretKey secretKey;
    private final long tokenDuration;

    public TokenService(@Value("${app.jjwt.secret}") String tokenSecret,
                        @Value("${app.jjwt.duration}") long tokenDuration) {
        this.tokenDuration = tokenDuration;
        this.secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes());
    }

    public String getTokenUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload()
                .getSubject();
    }

    private static String removeBearerPrefix(String token) {
        return token.startsWith("Bearer ")
                ? token.substring(7)
                : token;
    }
}
