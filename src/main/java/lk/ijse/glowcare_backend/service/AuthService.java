package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.AuthRequest;
import lk.ijse.glowcare_backend.dto.RegisterRequest;
import lk.ijse.glowcare_backend.dto.AuthResponse;
import java.util.Map;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    void completeProfile(Map<String, String> details);
}