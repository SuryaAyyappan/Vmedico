package com.vmmedico.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/doctors/**").permitAll()
                        .requestMatchers(
                                "/api/appointments/**",
                                "/api/register/**",       // Open registration endpoints
                                "/api/login",             // Open login
                                "/api/forgot-password",   // Open forgot password
                                "/api/change-password",
                                "/api/doctors"// Open change password endpoint
                        ).permitAll()
                        .anyRequest().authenticated() // Other endpoints require authentication
                )
                .httpBasic(httpBasic -> {}); // Basic auth for other endpoints

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
