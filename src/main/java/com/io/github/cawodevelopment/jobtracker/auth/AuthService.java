package com.io.github.cawodevelopment.jobtracker.auth;

import com.io.github.cawodevelopment.jobtracker.auth.dto.RegisterRequest;
import com.io.github.cawodevelopment.jobtracker.user.User;
import com.io.github.cawodevelopment.jobtracker.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request) {
        User user = new User();

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return "User registered successfully. Make sure to change this endpoint later.";
    }
}
