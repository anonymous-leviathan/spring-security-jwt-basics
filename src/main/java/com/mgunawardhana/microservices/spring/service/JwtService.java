package com.mgunawardhana.microservices.spring.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String extractUserEmail(String token) {
        return "userEmail";
    }
}