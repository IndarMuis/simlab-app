package simlabapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import simlabapp.dto.request.CreateAssistantRequest;
import simlabapp.entity.Account;
import simlabapp.entity.Assistant;
import simlabapp.entity.Role;
import simlabapp.repository.AccountRepository;
import simlabapp.repository.AssistantRepository;
import simlabapp.repository.RoleRepository;
import simlabapp.service.AssistantService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final AssistantRepository assistantRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(CreateAssistantRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
        }

        Role role = roleRepository.findById(3).get();
        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .build();
        Account saveAccount = accountRepository.save(account);

        Assistant assistant = Assistant.builder()
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .major(request.getMajor())
                .placeOfBirth(request.getPlaceOfBirth())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .account(saveAccount).build();
        assistantRepository.save(assistant);
    }
}
