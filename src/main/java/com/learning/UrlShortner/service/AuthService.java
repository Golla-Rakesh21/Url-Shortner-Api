package com.learning.UrlShortner.service;

import com.learning.UrlShortner.dto.AuthResponse;
import com.learning.UrlShortner.dto.LoginRequest;
import com.learning.UrlShortner.dto.RegisterRequest;
import com.learning.UrlShortner.entity.User;
import com.learning.UrlShortner.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository repo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository repo, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request){

        String email = request.getEmail().trim()
                .toLowerCase(Locale.ROOT);
        if (repo.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists");
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try{
            repo.saveAndFlush(user);
        }catch(DataIntegrityViolationException ex){
            throw new IllegalArgumentException(
                    "An account with this email already exists");
        }
        return new AuthResponse(
                jwtService.generateToken(email),email
        );

    }



    public AuthResponse login(LoginRequest request){

        String email = request.getEmail().trim()
                .toLowerCase(Locale.ROOT);
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,request.getPassword()
                )
        );
        String authenticatedEmail=authentication.getName();

        return new AuthResponse(
                jwtService.generateToken(authenticatedEmail),email
        );
    }

}
