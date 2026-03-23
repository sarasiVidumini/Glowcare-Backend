package lk.ijse.glowcare_backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.glowcare_backend.entity.*;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        boolean isNewUser = false;
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            isNewUser = true;
            String sessionRole = (String) request.getSession().getAttribute("oauth2_role");
            Role role = (sessionRole != null) ? Role.valueOf(sessionRole.toUpperCase()) : Role.CLIENT;

            // Save ONLY the base user.
            // The profile will be created in the AuthServiceImpl.completeProfile method.
            user = User.builder()
                    .email(email)
                    .name(name)
                    .role(role)
                    .authProvider("GOOGLE")
                    .build();

            userRepository.save(user);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        String targetUrl = "http://localhost:5173/oauth2/redirect?token=" + token + "&new=" + isNewUser;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}