package simlabapp.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Data
@Component
public class JwtConfig {

    private String secret;

    private String header;

    private String prefix;

    private Long expiration;

    private String issuer;

}
