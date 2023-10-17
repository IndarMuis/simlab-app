package simlabapp.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import simlabapp.entity.Account;
import simlabapp.security.jwt.JwtAuthConstant;
import simlabapp.security.jwt.JwtAuthService;
import simlabapp.service.impl.UserDetailsServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthService jwtAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        try {
            if (token != null && jwtAuthService.validateJwtToken(token)) {
                Claims claims = jwtAuthService.extractClaimsJwt(token);
                String username = (String) claims.get("username");
                Account account = (Account) userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid token");
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader(JwtAuthConstant.AUTH_HEADER);
        if (Strings.hasText(authorization) && authorization.startsWith(JwtAuthConstant.JWT_PREFIX)) {
            return authorization.substring(JwtAuthConstant.JWT_PREFIX.length());
        }
        return null;
    }
}
