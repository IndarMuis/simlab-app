package simlabapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import simlabapp.entity.Account;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtAuthService {

    public String generateToken(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Date expiredDate =
                Date.from(Instant.now().plusSeconds(JwtAuthConstant.JWT_EXPIRATION));
        return Jwts.builder()
                .claim("username", account.getUsername())
                .setHeaderParam("typ", "JWT")
                .setIssuer(JwtAuthConstant.JWT_ISSUER)
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
        byte[] bytes = Decoders.BASE64.decode(JwtAuthConstant.SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }

}
