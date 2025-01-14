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

    public String saveUser(UserCredential userCredential) {

        if(userCredentialRepository.findByUsername(userCredential.getUsername()).isPresent()) {
            return "Username already exists";
        }
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User saved successfully";
    }


    public String generateToken(String username) {
        String userId = userCredentialRepository.findUserIdByUsername(username).toString();
        return jwtService.generateToken(userId);
    }

    public String validateToken(String token) {
        String id = jwtService.extractUserId(token);
        System.out.println(id);
        return jwtService.validateToken(token) ? "Token is valid" : "Token is invalid";
    }
}
