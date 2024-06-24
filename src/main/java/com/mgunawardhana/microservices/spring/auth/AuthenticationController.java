package com.mgunawardhana.microservices.spring.auth;

import com.mgunawardhana.microservices.spring.domain.AuthenticationRequest;
import com.mgunawardhana.microservices.spring.domain.AuthenticationResponse;
import com.mgunawardhana.microservices.spring.domain.RegistrationRequest;
import com.mgunawardhana.microservices.spring.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest registrationRequest
    ){
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
