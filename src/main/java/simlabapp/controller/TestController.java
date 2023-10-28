package simlabapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello-auth")
    @PreAuthorize("hasRole('ASSISTANT_CANDIDATE')")
    public String helloAuth() {
        return "Test Hello Auth";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ASSISTANT_CANDIDATE')")
    public String hello() {
        return "Test Hello";
    }

}
