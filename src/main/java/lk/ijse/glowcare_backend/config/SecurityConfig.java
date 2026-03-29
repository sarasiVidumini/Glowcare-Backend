package lk.ijse.glowcare_backend.config;

import lk.ijse.glowcare_backend.security.CustomOAuth2AuthorizationRequestResolver;
import lk.ijse.glowcare_backend.security.OAuth2AuthenticationSuccessHandler;
import lk.ijse.glowcare_backend.util.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2AuthorizationRequestResolver customAuthorizationRequestResolver;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // 1. Allow pre-flight OPTIONS requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Public Auth Endpoints
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll()

                        // 3. Existing API Endpoints
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/routines/**").permitAll()
                        .requestMatchers("/api/v1/experts/**").permitAll()
                        .requestMatchers("/api/v1/routine-notifications/**").permitAll()

                        // 🤖 AI BOT CONFIGURATION
                        .requestMatchers("/api/v1/glowbot/**").permitAll()

                        // 🚀 WEBSOCKET & COMMUNITY CHAT CONFIGURATION
                        .requestMatchers("/ws/**").permitAll()                 // Opens the WebSocket connection
                        .requestMatchers("/api/v1/chat/**").permitAll()        // Opens Chat History, Verification & Uploads
                        .requestMatchers("/uploads/**").permitAll()            // Allows the frontend to view uploaded images

                        // 🏥 CLINICAL HUB CONFIGURATION
                        // Public: Anyone can view the map and the doctors
                        .requestMatchers(HttpMethod.GET, "/api/v1/clinical/physicians").permitAll()

                        // 🛡️ ADMIN ONLY: Forces JWT Authentication for adding/updating/deleting doctors
                        // (The Controller will then double-check that the JWT belongs to admin@glowcare.ai)
                        .requestMatchers(HttpMethod.POST, "/api/v1/clinical/physicians").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/clinical/physicians/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/clinical/physicians/**").authenticated()

                        // 4. Everything else must be authenticated (This implicitly protects /api/v1/clinical/book)
                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .authorizationRequestResolver(customAuthorizationRequestResolver)
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )

                .authenticationProvider(authenticateProvider())
                // This is the most important line - it checks for the Admin's JWT before the login page
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Your React URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticateProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}