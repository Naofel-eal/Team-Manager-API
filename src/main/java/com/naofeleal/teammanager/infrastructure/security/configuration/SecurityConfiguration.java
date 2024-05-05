package com.naofeleal.teammanager.infrastructure.security.configuration;

import com.naofeleal.teammanager.infrastructure.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter _jwtAuthenticationFilter;
    private final AuthenticationProvider _authenticationProvider;

    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ) {
        _jwtAuthenticationFilter = jwtAuthenticationFilter;
        _authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(_authenticationProvider)
            .addFilterBefore(_jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}