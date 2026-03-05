package com.evently.gateway.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JWTUtility;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static com.evently.gateway.config.constants.Paths.PUBLIC_PATHS;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final JWTUtility jwtUtility;

    public JwtAuthFilter(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing " +
                    "or malformed Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtility.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid " +
                    "or expired token");
            return;
        }

        long userId = jwtUtility.extractUserId(token);
        String role = jwtUtility.extractRoles(token).get(0);
        String email = jwtUtility.extractEmail(token);
        setSecurityContext(userId, role);

        filterChain.doFilter(withUserDetails(request, userId, role, email),
                response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return Arrays.stream(PUBLIC_PATHS)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private void setSecurityContext(long userId, String role) {
        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        authorities
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private HttpServletRequest withUserDetails(HttpServletRequest request,
                                               long userId, String role,
                                               String email) {
        String userIdValue = String.valueOf(userId);
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("X-User-Id".equalsIgnoreCase(name)) return userIdValue;
                if ("X-User-Role".equalsIgnoreCase(name)) return role;
                if ("X-User-Email".equalsIgnoreCase(name))
                    return email; // Added email
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if ("X-User-Id".equalsIgnoreCase(name))
                    return Collections.enumeration(List.of(userIdValue));
                if ("X-User-Role".equalsIgnoreCase(name))
                    return Collections.enumeration(List.of(role));
                if ("X-User-Email".equalsIgnoreCase(name))
                    return Collections.enumeration(List.of(email)); // Added
                // email
                return super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> names =
                        new java.util.ArrayList<>(Collections.list(super.getHeaderNames()));
                names.add("X-User-Id");
                names.add("X-User-Role");
                names.add("X-User-Email"); // Added email
                return Collections.enumeration(names);
            }
        };
    }
}