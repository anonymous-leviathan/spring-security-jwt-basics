package com.mgunawardhana.microservices.spring.authorized;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authorized")
public class AuthorizedController {

    @GetMapping
    public ResponseEntity<String> helloFromSecuredController() {
        return ResponseEntity.ok("Hello from Secured Controller that guy is Authorized!");
    }
}
