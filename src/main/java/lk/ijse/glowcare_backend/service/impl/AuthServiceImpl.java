package lk.ijse.glowcare_backend.service.impl;

// Update these imports if you moved them to .dto.request / .dto.response
import lk.ijse.glowcare_backend.dto.AuthRequest;
import lk.ijse.glowcare_backend.dto.RegisterRequest;
import lk.ijse.glowcare_backend.dto.AuthResponse;

import lk.ijse.glowcare_backend.entity.ClientProfile;
import lk.ijse.glowcare_backend.entity.DoctorProfile;
import lk.ijse.glowcare_backend.entity.ExpertProfile;
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

        // 2. Create the base User entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .authProvider("LOCAL")
                .build();

        // 3. Automatically create the specific Profile based on the Role
        switch (request.getRole()) {
            case CLIENT:
                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setUser(user);
                user.setClientProfile(clientProfile); // Link bidirectionally
                break;

            case DOCTOR:
                if (request.getLicenseNumber() == null || request.getLicenseNumber().trim().isEmpty()) {
                    throw new RuntimeException("License Number is required for Doctors!");
                }
                DoctorProfile doctorProfile = new DoctorProfile();
                doctorProfile.setUser(user);
                doctorProfile.setLicenseNumber(request.getLicenseNumber());
                user.setDoctorProfile(doctorProfile); // Link bidirectionally
                break;

            case EXPERT:
                if (request.getLicenseNumber() == null || request.getLicenseNumber().trim().isEmpty()) {
                    throw new RuntimeException("License Number is required for Clinical Experts!");
                }
                ExpertProfile expertProfile = new ExpertProfile();
                expertProfile.setUser(user);
                expertProfile.setLicenseNumber(request.getLicenseNumber());
                user.setExpertProfile(expertProfile); // Link bidirectionally
                break;

            case ADMIN:
                // Admins typically don't need a public profile table, so we just pass
                break;
        }

        // 4. Save to database.
        // Thanks to CascadeType.ALL, this single save will insert the User AND their Profile into the DB!
        userRepository.save(user);

        // 5. Generate JWT Token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 6. Return success response to React
        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("Account created successfully as " + request.getRole())
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