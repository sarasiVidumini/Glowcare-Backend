package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.AuthRequest;
import lk.ijse.glowcare_backend.dto.RegisterRequest;
import lk.ijse.glowcare_backend.dto.AuthResponse;
import lk.ijse.glowcare_backend.entity.*;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.service.AuthService;
import lk.ijse.glowcare_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Check for duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Build the base User Entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .authProvider("LOCAL")
                .build();

        // 3. Conditional Profile Creation (Directly from UI fields)
        if (request.getRole() == Role.EXPERT) {
            ExpertProfile profile = ExpertProfile.builder()
                    .user(user)
                    .licenseNumber(request.getLicenseNumber())
                    .expertiseArea(request.getExpertiseArea())
                    .bio(request.getBio())
                    .build();
            user.setExpertProfile(profile);
        } else {
            ClientProfile profile = new ClientProfile();
            profile.setUser(user);
            user.setClientProfile(profile);
        }

        // 4. Save to Database
        userRepository.save(user);

        // 5. Generate and Return Token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public void completeProfile(Map<String, String> details) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.CLIENT) {
            ClientProfile profile = user.getClientProfile();
            if (profile == null) {
                profile = new ClientProfile();
                profile.setUser(user);
            }
            profile.setSkinType(details.get("skinType"));
            user.setClientProfile(profile);

        } else if (user.getRole() == Role.EXPERT) {
            ExpertProfile profile = user.getExpertProfile();
            if (profile == null) {
                profile = new ExpertProfile();
                profile.setUser(user);
            }
            profile.setLicenseNumber(details.get("licenseNumber"));
            user.setExpertProfile(profile);
        }

        userRepository.save(user);
    }
}