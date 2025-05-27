package com.proyecto.buckys_vet.security;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Excluir rutas que no necesitan autenticación JWT
        String path = request.getRequestURI();
        System.out.println("=== JWT FILTER === Path: " + path);

        if (path.startsWith("/h2-console/") || path.startsWith("/h2/") || path.startsWith("/login/")
                || path.startsWith("/home/")
                || path.startsWith("/error/")) {
            System.out.println("Ruta excluida del JWT filter: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJWT(request);
        System.out.println("Token JWT: " + (token != null ? "Presente" : "Ausente"));

        if (token != null && jwtGenerator.validateToken(token)) {
            String username = jwtGenerator.ExtractUsername(token);
            System.out.println("Token válido para usuario: " + username);

            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            System.out.println("Authorities del usuario: " + userDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("Autenticación establecida para: " + username);
        } else {
            System.out.println("Token JWT inválido o ausente");
        }
        filterChain.doFilter(request, response);
    }

    private String getJWT(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
