package simlabapp.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import simlabapp.security.handler.CustomAccessDeniedHandler;
import simlabapp.security.handler.CustomAuthEntryPointHandler;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthEntryPointHandler customAuthEntryPointHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(
                        new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration corsConfiguration = new CorsConfiguration();
                                corsConfiguration.setAllowCredentials(true);
                                corsConfiguration.setAllowedOrigins(List.of("*"));
                                corsConfiguration.setAllowedMethods(List.of("*"));
                                corsConfiguration.setAllowedHeaders(List.of("*"));
                                corsConfiguration.setExposedHeaders(List.of("Authorization"));
                                corsConfiguration.setMaxAge(3600L);
                                return corsConfiguration;
                            }
                        }
                ))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthEntryPointHandler)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v1/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html",
                                "/api/v1/**").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}
