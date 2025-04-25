package com.example.api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.api.modules.auth.service.AuthUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getRequestURI();

        if (requestPath.equals("/api/auth/login") || requestPath.equals("/api/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
                String authToken = authorizationHeader.substring(7);
                String username = this.jwtService.getUsernameFromToken(authToken);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.authUserDetailsService.loadUserByEmailOrUsername(username);

                    if (jwtService.validateToken(authToken)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token") {
            };
        }
    }
}
