package simlabapp.security.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JwtAuthConstant {

    @Value("${jwt.header}")
    public static String AUTH_HEADER;

    @Value(("${jwt.prefix}"))
    public static String JWT_PREFIX;

    @Value("${jwt.expiration}")
    public static Long JWT_EXPIRATION;

    @Value("${jwt.issuer}")
    public static String JWT_ISSUER;

    @Value("${jwt.secret}")
    public static String SECRET;

}
