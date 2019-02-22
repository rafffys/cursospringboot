package br.com.caelum.forum.security.jwt;

import br.com.caelum.forum.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {
    @Value("${caelum.forum.jwt.secret}")
    private String secret;
    @Value("${caelum.forum.jwt.expiration}")
    private long expirationInMillis;

    public String generateToken(Authentication auth) {
        User user = (User) auth.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationInMillis);

        return Jwts.builder()
                .setIssuer("Caelum Forum API")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();

    }
}
