package com.mgunawardhana.microservices.spring.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/authorized")
public class AuthorizedController {

    @GetMapping
    public ResponseEntity<String> helloFromSecuredController() {
        log.info("Hello from Secured Controller, That Guy is Authorized!");
        return ResponseEntity.ok("Hello from Secured Controller that guy is Authorized!");
    }
}
