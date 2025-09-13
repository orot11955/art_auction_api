package com.sparta.camp.java.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.camp.java.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    log.error("Access denied error: {}", accessDeniedException.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
        .errorCode("FORBIDDEN")
        .message("Access denied")
        .timestamp(System.currentTimeMillis())
        .build();

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), errorResponse);
  }
}

