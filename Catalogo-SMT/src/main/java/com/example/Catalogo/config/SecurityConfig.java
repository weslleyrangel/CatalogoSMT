package com.example.Catalogo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança corrigida para Spring Boot 3.x
 * Remove dependências legadas do javax.servlet
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF para APIs REST
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configuração de autorização usando a nova sintaxe
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/actuator/**").permitAll() // Para health checks
                .anyRequest().authenticated()
            )
            
            // Configuração de headers para permitir H2 Console
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );

        return http.build();
    }
}