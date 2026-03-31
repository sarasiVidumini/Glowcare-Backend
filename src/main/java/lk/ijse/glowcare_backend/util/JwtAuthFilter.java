package lk.ijse.glowcare_backend.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 🚨 REMOVED the manual OPTIONS block. Spring's .cors() in SecurityConfig handles this safely.

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        try {
            final String username = jwtUtil.extractUsername(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwtToken)) {
                    List<SimpleGrantedAuthority> authorities;
                    // 🛡️ Explicitly grant ADMIN to the superuser
                    if (username.equalsIgnoreCase("admin@glowcare.ai")) {
                        authorities = Arrays.asList(
                                new SimpleGrantedAuthority("ADMIN"),
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        );
                    } else {
                        authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // 🚨 THE FIX: Do NOT return a 401 here!
            // If the token is bad, expired, or the OAuth2 user isn't in the DB yet,
            // we just clear the context so they act as an "anonymous" guest.
            // If the endpoint is permitAll() (like /analysis), Spring lets them through!
            // If the endpoint is authenticated(), Spring throws a proper 401 later.
            SecurityContextHolder.clearContext();
            System.err.println("JWT processing skipped for this request: " + e.getMessage());
        }

        // 🚨 Always continue the chain!
        filterChain.doFilter(request, response);
    }
}