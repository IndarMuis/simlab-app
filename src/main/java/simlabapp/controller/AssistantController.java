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
import simlabapp.dto.request.CreateAssistantRequest;
import simlabapp.dto.response.WebResponse;
import simlabapp.service.AssistantService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assistants")
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<?>> register(@RequestBody @Valid CreateAssistantRequest request) {
        assistantService.register(request);
        return ResponseEntity.ok().body(
                WebResponse.builder()
                        .code(HttpStatus.OK.value())
                        .status("OK").build()
        );
    }

}
