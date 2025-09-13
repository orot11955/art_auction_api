package com.sparta.camp.java.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();

    corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));

    corsConfig.setAllowedMethods(Arrays.asList(
        "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

    corsConfig.setAllowedHeaders(Arrays.asList(
        "Authorization", "Content-Type", "X-Requested-With",
        "Accept", "Origin", "Access-Control-Request-Method",
        "Access-Control-Request-Headers", "X-Service-Name",
        "X-Request-ID", "X-User-ID"));

    corsConfig.setExposedHeaders(Arrays.asList(
        "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
        "X-Total-Count", "X-Page-Count"));

    corsConfig.setAllowCredentials(true);

    corsConfig.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}

