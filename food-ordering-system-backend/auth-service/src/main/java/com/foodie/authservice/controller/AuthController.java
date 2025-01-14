package com.foodie.authservice.controller;


import com.foodie.authservice.dto.AuthRequest;
import com.foodie.authservice.model.UserCredential;
import com.foodie.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/greet")
    public String greet(){
        return "Hello";
    }

    @PostMapping("/register")
    public String saveUser(@RequestBody UserCredential userCredential) {
        return authService.saveUser(userCredential);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()) {
            System.out.println("User authenticated");
            return authService.generateToken(authRequest.getUsername());
        } else {
            return "Invalid credentials";
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        return authService.validateToken(token);
    }
}
