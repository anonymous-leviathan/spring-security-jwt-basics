package com.mgunawardhana.microservices.spring.service;

import com.mgunawardhana.microservices.spring.domain.request.AuthenticationRequest;
import com.mgunawardhana.microservices.spring.domain.request.RegistrationRequest;
import com.mgunawardhana.microservices.spring.domain.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest registrationRequest);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
