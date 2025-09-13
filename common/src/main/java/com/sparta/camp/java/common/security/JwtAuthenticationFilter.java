package com.sparta.camp.java.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = getTokenFromRequest(request);

    if (token != null && jwtTokenProvider.validateToken(token)) {
      try {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        String role = jwtTokenProvider.getRoleFromToken(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            username,
            null,
            List.of(new SimpleGrantedAuthority("ROLE_" + role)));

        // User ID를 authentication details에 추가
        authentication.setDetails(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("Set Authentication in SecurityContext for user: {}", username);

      } catch (Exception e) {
        log.error("Cannot set user authentication: {}", e.getMessage());
        SecurityContextHolder.clearContext();
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}

