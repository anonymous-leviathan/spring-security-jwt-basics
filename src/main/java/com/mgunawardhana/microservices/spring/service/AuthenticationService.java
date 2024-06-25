package com.mgunawardhana.microservices.spring.service;

import com.mgunawardhana.microservices.spring.domain.AuthenticationRequest;
import com.mgunawardhana.microservices.spring.domain.AuthenticationResponse;
import com.mgunawardhana.microservices.spring.domain.RegistrationRequest;
import com.mgunawardhana.microservices.spring.repository.UserRepository;
import com.mgunawardhana.microservices.spring.user.Role;
import com.mgunawardhana.microservices.spring.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        var user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(Role.USER)
                .build();

        log.info("AuthenticationResponse From Register: {}", user.toString());

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.info("Generated Token from Register Function: {}", jwtToken);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        log.info("AuthenticationResponse From Authenticate Function: {}", user);

        var jwtToken = jwtService.generateToken(user);

        log.info("Generated Token from Authenticate Function: {}", jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
