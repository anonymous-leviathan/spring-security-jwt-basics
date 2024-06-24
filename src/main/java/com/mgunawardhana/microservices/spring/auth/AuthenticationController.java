package com.mgunawardhana.microservices.spring.auth;

import com.mgunawardhana.microservices.spring.domain.AuthenticationResponse;
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

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest registrationRequest
    ){
        //TODO: Implement registration logic
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){

    }

}
