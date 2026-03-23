package lk.ijse.glowcare_backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.glowcare_backend.entity.ClientProfile;
import lk.ijse.glowcare_backend.entity.Role;
import lk.ijse.glowcare_backend.entity.User;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 1. Get user details from Google
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");

        // 2. Fetch the role they selected from the session (Fallback to CLIENT if missing)
        Role role = Role.CLIENT;
        String requestedRole = (String) request.getSession().getAttribute("oauth2_role");
        if (requestedRole != null) {
            try {
                role = Role.valueOf(requestedRole.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        // 3. Check if user exists in the database
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // NEW USER: Register them automatically!
            user = User.builder()
                    .email(email)
                    .name(name)
                    .role(role)
                    .authProvider("GOOGLE")
                    .providerId(googleId)
                    .build();

            // Setup profile (Usually Google sign-ups are Clients. Doctors/Experts require manual license verification later)
            if (role == Role.CLIENT) {
                ClientProfile profile = new ClientProfile();
                profile.setUser(user);
                user.setClientProfile(profile);
            }

            userRepository.save(user);
        }

        // 4. Generate the JWT Token for the User
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        // 5. Redirect back to React frontend with the token securely in the URL!
        // NOTE: Adjust the port (5173 or 5174) based on where your Vite React app is running
        String targetUrl = "http://localhost:5173/oauth2/redirect?token=" + token;

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}