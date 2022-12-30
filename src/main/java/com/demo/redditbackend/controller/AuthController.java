package com.demo.redditbackend.controller;

import com.demo.redditbackend.dto.AuthenticationResponse;
import com.demo.redditbackend.dto.LoginRequest;
import com.demo.redditbackend.dto.SignupRequest;
import com.demo.redditbackend.exception.SpringRedditException;
import com.demo.redditbackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<String> singup(@RequestBody SignupRequest signupRequest) {
        this.authService.signup(signupRequest);
        return new ResponseEntity<String>("User Registration Successful", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        try {
            this.authService.verifyToken(token);
            return new ResponseEntity<String>("Verified", HttpStatus.OK);
        } catch (SpringRedditException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = this.authService.login(loginRequest);
        return new ResponseEntity<AuthenticationResponse>(response, response == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
