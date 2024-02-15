package com.venkat.controller;

import com.venkat.service.AuthenticationService;
import com.venkat.vo.AuthRequest;
import com.venkat.vo.AuthResponse;
import com.venkat.vo.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authService;

    //constructor injection
    public AuthenticationController(AuthenticationService service) {
        this.authService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest userRequest){
        logger.info("Creating the user ");
        //used global exception handler

        /*if(authService.getUser(userRequest.email())){
            throw new ContentAPIRequestException("User already exists ");
        }
        if(userRequest.firstName().isEmpty() || userRequest.lastName().isEmpty()
                || userRequest.email().isEmpty() || userRequest.password().isEmpty()){
            throw new ContentAPIRequestException("All fields should have values");
        }*/
        AuthResponse response = authService.register(userRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest){
        logger.info("Authenticating the user ");
        //used global exception handler

        /*try {
            AuthResponse authResponse = authService.authenticate(authRequest);
            return ResponseEntity.ok(authResponse);
        }catch (RuntimeException exception){
            throw new ContentAPIRequestException("Username or password is incorrect");
        }*/
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<AuthResponse> forgotPassword(@RequestBody AuthRequest authRequest){
        logger.info("Authenticating the user ");
        /*if(authRequest.email().isEmpty() || authRequest.password().isEmpty()){
            throw new ContentAPIRequestException("All fields should have values");
        }
        try {
            AuthResponse authResponse = authService.forgotPassword(authRequest);
            return ResponseEntity.ok(authResponse);
        }catch (RuntimeException exception){
            throw new ContentAPIRequestException("Unable update the password");
        }*/
        //used global exception handler
        AuthResponse authResponse = authService.forgotPassword(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
