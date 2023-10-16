package simlabapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simlabapp.dto.request.LoginAccountRequest;
import simlabapp.dto.response.LoginAccountResponse;
import simlabapp.dto.response.WebResponse;
import simlabapp.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<WebResponse<?>> login(@RequestBody @Valid LoginAccountRequest request) {
        LoginAccountResponse response = accountService.login(request);

        return ResponseEntity.ok().body(
                WebResponse.builder()
                        .code(HttpStatus.OK.value())
                        .status("OK")
                        .data(response).build()
        );
    }

}
