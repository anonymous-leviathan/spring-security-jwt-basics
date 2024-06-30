package com.mgunawardhana.microservices.spring.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgunawardhana.microservices.spring.domain.Role;
import com.mgunawardhana.microservices.spring.domain.User;
import com.mgunawardhana.microservices.spring.domain.request.AuthenticationRequest;
import com.mgunawardhana.microservices.spring.domain.request.RegistrationRequest;
import com.mgunawardhana.microservices.spring.domain.response.AuthenticationResponse;
import com.mgunawardhana.microservices.spring.repository.UserRepository;
import com.mgunawardhana.microservices.spring.service.AuthenticationService;
import com.mgunawardhana.microservices.spring.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @NotNull
    private final UserRepository userRepository;

    @NotNull
    private final PasswordEncoder passwordEncoder;

    @NotNull
    private final JwtService jwtService;

    @NotNull
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        var user = User.builder().firstName(registrationRequest.getFirstName()).lastName(registrationRequest.getLastName()).email(registrationRequest.getEmail()).password(passwordEncoder.encode(registrationRequest.getPassword())).role(Role.USER).build();

        log.info("AuthenticationResponse From Register: {}", user.toString());

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        log.info("Generated Token from Register Function: {}", jwtToken);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        log.info("AuthenticationResponse From Authenticate Function: {}", user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        log.info("Generated Token from Authenticate Function: {}", jwtToken);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Authorization Header is Null or Not Started with Bearer");
            return;
        }

        refreshToken = authorizationHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userRepository.findByEmail(userEmail).orElseThrow();
            log.info("User Details from Refresh Token: {}", userDetails);

            if (jwtService.isTokenValidated(refreshToken, userDetails)) {

                var accessToken = jwtService.generateToken(userDetails);
                log.info("Generated Token from Refresh Token Function: {}", accessToken);

                var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                log.info("Authentication Response from Refresh Token Function: {}", authResponse);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
