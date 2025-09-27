package com.example.demo_rest_2.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper mapper;

    public SecurityConfig(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/welcome").permitAll()
                        .requestMatchers("/auth/user").hasRole("USER")
                        .requestMatchers("/auth/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                //.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // <-- тут
                )
                .httpBasic(withDefaults())
                .exceptionHandling(ex -> ex
                        // 401 Unauthorized
                        .authenticationEntryPoint((request, response, authException) -> {

                            Map<String,Object> body = new LinkedHashMap<>();
                            body.put("timestamp", Instant.now().toString());
                            body.put("status", HttpStatus.UNAUTHORIZED.value());
                            body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase()); // "Forbidden"
                            body.put("message", "Authentication failed");
                            body.put("path", request.getRequestURI());

                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                            mapper.writeValue(response.getOutputStream(), body);
                        })
                        // 403 Forbidden
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                                    HttpStatus.FORBIDDEN, "Access denied"
                            );
                            // Добавляем дополнительные поля
                            pd.setInstance(URI.create(request.getRequestURI()));
                            pd.setProperty("timestamp", Instant.now().toString());
                            pd.setProperty("path", request.getRequestURI());

                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/problem+json");

                            mapper.writeValue(response.getWriter(), pd);
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}