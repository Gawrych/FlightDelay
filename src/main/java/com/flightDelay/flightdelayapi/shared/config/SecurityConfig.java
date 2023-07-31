package com.flightDelay.flightdelayapi.shared.config;

import com.flightDelay.flightdelayapi.shared.auth.DisableCsrfProtectionCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.http.auth-token-header-name}")
    private String AUTH_TOKEN_HEADER_NAME;

    @Value("${app.http.auth-token}")
    private String AUTH_TOKEN;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(new DisableCsrfProtectionCustomizer());

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}