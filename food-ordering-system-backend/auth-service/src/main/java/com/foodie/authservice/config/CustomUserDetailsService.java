package com.foodie.authservice.config;

import com.foodie.authservice.model.Admin;
import com.foodie.authservice.model.UserCredential;
import com.foodie.authservice.repository.AdminRepository;
import com.foodie.authservice.repository.UserCredentialRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (isAdminLoginRequest(request)) {
            Optional<Admin> admin = adminRepository.findByUsername(username);
            if (admin.isPresent()) {
                return new CustomAdminDetails(admin.get());
            }
        } else {
            Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
            if (userCredential.isPresent()) {
                return new CustomUserDetails(userCredential.get());
            }
        }

        throw new UsernameNotFoundException("User or Admin not found with username: " + username);
    }

    private boolean isAdminLoginRequest(HttpServletRequest request) {
        return "/admin/login".equals(request.getRequestURI());
    }
}
