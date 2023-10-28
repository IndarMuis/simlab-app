package simlabapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import simlabapp.dto.request.CreateAssistantRequest;
import simlabapp.entity.Account;
import simlabapp.entity.Assistant;
import simlabapp.entity.Role;
import simlabapp.repository.AccountRepository;
import simlabapp.repository.AssistantRepository;
import simlabapp.repository.RoleRepository;
import simlabapp.service.AssistantService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private final AssistantRepository assistantRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourceLoader resourceLoader;

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

    @Override
    public void uploadImageProfile(MultipartFile image) throws IOException {
        try {
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, Path.of("src/main/resources/documents/" + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
