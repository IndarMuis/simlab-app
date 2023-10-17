package simlabapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import simlabapp.dto.request.LoginAccountRequest;
import simlabapp.dto.response.LoginAccountResponse;
import simlabapp.entity.Account;
import simlabapp.repository.AccountRepository;
import simlabapp.security.jwt.JwtAuthService;
import simlabapp.service.AccountService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthService jwtAuthService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginAccountResponse login(LoginAccountRequest request) {
        Account account = (Account) userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid username or password");
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword(), account.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtAuthService.generateToken(authentication);
        return LoginAccountResponse.builder()
                .username(account.getUsername())
                .accessToken(accessToken).build();

    }
}
