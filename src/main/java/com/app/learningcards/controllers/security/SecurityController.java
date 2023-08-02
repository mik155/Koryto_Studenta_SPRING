package com.app.learningcards.controllers.security;

import com.app.learningcards.request.config.AuthenticationRequest;
import com.app.learningcards.request.config.AuthenticationResponse;
import com.app.learningcards.request.config.RegisterRequest;
import com.app.learningcards.services.security.AuthenticationService;
import com.app.learningcards.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class SecurityController
{
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>  registerUser(@RequestBody RegisterRequest registerRequest)
    {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest)
    {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
