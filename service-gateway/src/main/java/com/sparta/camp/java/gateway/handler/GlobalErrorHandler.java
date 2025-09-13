package com.sparta.camp.java.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ServerHttpResponse response = exchange.getResponse();

    if (response.isCommitted()) {
      return Mono.error(ex);
    }

    response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now().toString());
    errorResponse.put("path", exchange.getRequest().getURI().getPath());

    if (ex instanceof NotFoundException) {
      response.setStatusCode(HttpStatus.NOT_FOUND);
      errorResponse.put("error", "Service Not Found");
      errorResponse.put("message", "The requested service is not available");
      errorResponse.put("status", 404);
    } else if (ex instanceof ResponseStatusException) {
      ResponseStatusException statusEx = (ResponseStatusException) ex;
      response.setStatusCode(statusEx.getStatusCode());
      errorResponse.put("error", statusEx.getReason());
      errorResponse.put("status", statusEx.getStatusCode().value());
    } else {
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      errorResponse.put("error", "Internal Server Error");
      errorResponse.put("message", "An unexpected error occurred");
      errorResponse.put("status", 500);
    }

    log.error("Error occurred: {}", ex.getMessage(), ex);

    try {
      String jsonResponse = objectMapper.writeValueAsString(errorResponse);
      DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
      return response.writeWith(Mono.just(buffer));
    } catch (Exception e) {
      log.error("Error writing error response", e);
      return Mono.error(e);
    }
  }
}

