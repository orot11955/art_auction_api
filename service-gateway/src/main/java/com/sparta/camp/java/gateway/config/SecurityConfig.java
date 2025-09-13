package com.sparta.camp.java.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
        .csrf(csrf -> csrf.disable())
        .authorizeExchange(exchanges -> exchanges
            // // public
            // .pathMatchers("/api/auth/signup", "/api/auth/login",
            // "/api/auth/refresh").permitAll()
            // .pathMatchers("/api/**/health").permitAll()
            // .pathMatchers("/api/artworks/**").permitAll()

            // // protected
            // .pathMatchers("/api/auctions/**").authenticated()
            // .pathMatchers("/api/bids/**").authenticated()
            // .pathMatchers("/api/settlements/**").authenticated()
            // .pathMatchers("/api/notifications/**").authenticated()

            // // admin only
            // .pathMatchers("/api/admin/**").hasRole("ADMIN")

            // .anyExchange().authenticated())
            // .oauth2ResourceServer(oauth2 -> oauth2
            // .jwt(jwt -> jwt.jwkSetUri("http://service-auth:8081/.well-known/jwks.json")))
            // TODO: 테스트 환경에서는 모든 경로 허용
            .anyExchange().permitAll())
        .build();
  }
}
