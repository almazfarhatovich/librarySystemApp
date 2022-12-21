package appLibrary.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "app.security.jwt")
@Getter
@Setter
public class JwtUtils {

    private String issuer;

    private String secret;

    private long expiresAt;

    public String generateToken(String email) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withClaim("email", email)
                .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(expiresAt).toInstant()))
                .sign(Algorithm.HMAC512(secret));
    }

    public String verifyToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }
}