package com.sparta.camp.java.auth.config;

import com.sparta.camp.java.common.security.JwtAccessDeniedHandler;
import com.sparta.camp.java.common.security.JwtAuthenticationEntryPoint;
import com.sparta.camp.java.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtFilter;
  private final JwtAuthenticationEntryPoint entryPoint;
  private final JwtAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(entryPoint)
            .accessDeniedHandler(accessDeniedHandler))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/auth/health").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)  throws Exception {
    return cfg.getAuthenticationManager();
  }

}
