package simlabapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final JwtConfig jwtConfig;

    public String generateToken(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        Date expiredDate =
                Date.from(Instant.now().plusSeconds(jwtConfig.getExpiration()));
        return Jwts.builder()
                .claim("username", username)
                .setHeaderParam("typ", "JWT")
                .setIssuer(jwtConfig.getIssuer())
                .setExpiration(expiredDate)
                .signWith(generateSecretKey(), SignatureAlgorithm.HS256).compact();
    }

    public Claims extractClaimsJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateSecretKey())
                .build().parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(generateSecretKey())
                    .build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | IllegalIdentifierException | SignatureException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token invalid");
        } catch (ExpiredJwtException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "expired token");
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    private Key generateSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(bytes);
    }

}
