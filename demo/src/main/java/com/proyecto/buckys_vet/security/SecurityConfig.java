package com.proyecto.buckys_vet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(request -> request
                        // Rutas públicas - H2 debe estar PRIMERO
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/error/**").permitAll()

                        // Rutas específicas para DUENO - pueden acceder a su propia información
                        .requestMatchers("/duenos/**").hasAnyRole("DUENO", "VETERINARIO", "ADMIN")

                        // Rutas específicas para VETERINARIO y ADMIN
                        .requestMatchers("/veterinarios/**").hasAnyRole("VETERINARIO", "ADMIN")
                        .requestMatchers("/tratamientos/**").hasAnyRole("VETERINARIO", "ADMIN")

                        // Rutas específicas para ADMIN únicamente
                        .requestMatchers("/api/medicamentos/**").hasAnyRole("VETERINARIO", "ADMIN")

                        // Rutas de mascotas - DUENO puede ver sus mascotas, VETERINARIO y ADMIN pueden
                        // ver todas
                        .requestMatchers("/api/mascotas/**").hasAnyRole("DUENO", "VETERINARIO", "ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthEntryPoint()));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
