package com.taskmanager.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.application.config.JwtProvider;
import com.taskmanager.application.dto.AuthRequest;
import com.taskmanager.application.dto.AuthResponse;
import com.taskmanager.application.dto.RegisterRequest;
import com.taskmanager.application.entity.Role;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            String token = jwtProvider.generateToken(userOpt.get().getUsername());
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
