package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.demo.dto.LoginRequest;
import org.example.demo.dto.RegisterRequest;
import org.example.demo.entity.AppUser;
import org.example.demo.exception.ResourceNotFoundException;
import org.example.demo.repository.UserRepository;
import org.example.demo.security.JwtUtil;
import org.example.demo.security.Role;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepo,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Register a new user (ADMIN/INSTRUCTOR/STUDENT)")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        Role role;
        try {
            role = Role.valueOf(req.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Invalid role: " + req.getRole());
        }

        String encoded = passwordEncoder.encode(req.getPassword());
        AppUser user = new AppUser(req.getUsername(), encoded, role);
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
    }

    @Operation(summary = "Login with username/password and receive JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth;
        try {
            auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(), req.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token);
    }
}
