package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.AuthRequest;
import lk.ijse.glowcare_backend.dto.RegisterRequest;
import lk.ijse.glowcare_backend.dto.AuthResponse;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.AuthService;
import lk.ijse.glowcare_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // 1. Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Create the new user and encode their password
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .authProvider("LOCAL")
                .build();

        // 3. Save to database
        userRepository.save(user);

        // 4. Generate JWT Token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 5. Return success response to React
        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("User registered successfully")
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // 1. Let Spring Security verify the email and password against the database
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. If we reach here, credentials are correct. Fetch the user.
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate a new JWT Token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 4. Return to React
        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
}