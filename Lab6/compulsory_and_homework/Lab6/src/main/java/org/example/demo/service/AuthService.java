package org.example.demo.service;

import org.example.demo.entity.AppUser;
import org.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public AppUser registerStudent(String username, String email, String rawPassword) {

        AppUser ua = new AppUser();
        ua.setUsername(username);
        ua.setEmail(email);
        ua.setPassword(encoder.encode(rawPassword));

        HashSet<String> roles = new HashSet<>();
        roles.add("STUDENT");
        ua.setRoles(roles);

        return repo.save(ua);
    }
}
