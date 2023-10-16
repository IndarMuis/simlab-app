package simlabapp.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class JwtAuthConstant {

    @Value("${jwt.header}")
    public static String AUTH_HEADER = "Authorization";

    @Value(("${jwt.prefix}"))
    public static String JWT_PREFIX = "Bearer ";

    @Value("${jwt.expiration}")
    public static Long JWT_EXPIRATION = 70000L;

    @Value("${jwt.issuer}")
    public static String JWT_ISSUER = "simlab-service";

    @Value("${jwt.secret}")
    public static String SECRET = "NcpY92ALsniDR2K8I/YiJbl5Jl3MWlS5GbmtzMqQj0MaHgNIsjzKTNmLrDZ7Uy0C2orSFnn/gmyvYWHIXV2JtrUEpvrDKnsaAywJVvG3xrc=";

}
