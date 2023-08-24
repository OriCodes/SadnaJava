package com.forums.forum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path="/register")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(path ="/authenticate")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping(path = "/hello")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World");
    }
}
