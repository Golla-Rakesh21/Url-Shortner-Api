//package com.learning.UrlShortner.controller;
//
//import com.learning.UrlShortner.dto.AuthResponse;
//import com.learning.UrlShortner.dto.LoginRequest;
//import com.learning.UrlShortner.dto.RegisterRequest;
//import com.learning.UrlShortner.service.AuthService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(
//            @Valid @RequestBody RegisterRequest request) {
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(authService.register(request));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(
//            @Valid @RequestBody LoginRequest request) {
//
//        return ResponseEntity.ok(authService.login(request));
//    }
//}


package com.learning.UrlShortner.controller;

import com.learning.UrlShortner.dto.AuthResponse;
import com.learning.UrlShortner.dto.LoginRequest;
import com.learning.UrlShortner.dto.RegisterRequest;
import com.learning.UrlShortner.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Authentication",
        description = """
        APIs for user registration and authentication.

        Users can create an account and authenticate
        using their registered email and password.

        Successful login returns a JWT access token
        required for accessing protected endpoints.
        """
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // REGISTER
    @PostMapping("/register")
    @Operation(summary = "Takes the email and password as inputs for registering and jwt generation")
    public ResponseEntity<AuthResponse> register(

            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // LOGIN
    @PostMapping("/login")
    @Operation(summary = "Takes the email and password as inputs for login and jwt generation")
    public ResponseEntity<AuthResponse> login(

            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}