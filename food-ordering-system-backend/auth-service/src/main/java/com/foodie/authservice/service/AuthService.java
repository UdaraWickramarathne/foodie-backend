package com.foodie.authservice.service;

import com.foodie.authservice.model.UserCredential;
import com.foodie.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void saveUser(UserCredential userCredential) {

        if (userCredentialRepository.findByUsername(userCredential.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
    }


    public String loginUser(String username) {
        String userId = userCredentialRepository.findUserIdByUsername(username).toString();
        return jwtService.generateToken(userId);
    }

    public boolean validateToken(String token) {
        String id = jwtService.extractUserId(token);
        return jwtService.validateToken(token);
    }

    public String getUserIdFromToken(String token) {
        return jwtService.extractUserId(token);
    }

}
