package com.mgunawardhana.microservices.spring.auth;

import com.mgunawardhana.microservices.spring.domain.AuthenticationRequest;
import com.mgunawardhana.microservices.spring.domain.AuthenticationResponse;
import com.mgunawardhana.microservices.spring.domain.RegistrationRequest;
import com.mgunawardhana.microservices.spring.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        log.info("RegistrationRequest: {}", registrationRequest.toString());
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        log.info("AuthenticationRequest: {}", request.toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
