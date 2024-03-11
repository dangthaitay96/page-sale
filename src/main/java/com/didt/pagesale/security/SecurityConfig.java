package com.didt.pagesale.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthenticationFilter filter;

    public SecurityConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter) {
        this.point = point;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(Arrays.asList("*"));
                            configuration.setAllowedMethods(Arrays.asList("*"));
                            configuration.setAllowedHeaders(Arrays.asList("*"));
                            return configuration;
                        }))

                .authorizeRequests()
                .requestMatchers("/courses/create", "/courses/:id", "/courses/listCourses", "/courses/update", "/feedbacks/**").authenticated()
                .requestMatchers("/auth/login", "/anonymous/**", "/courses/files/**").permitAll()
                .anyRequest()

                .authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
