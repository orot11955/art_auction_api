package com.sparta.camp.java.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends AbstractGatewayFilterFactory<RequestLoggingFilter.Config> {

  public RequestLoggingFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      String requestId = UUID.randomUUID().toString();

      ServerHttpRequest mutatedRequest = request.mutate()
          .header("X-Request-ID", requestId)
          .build();

      log.info("=== REQUEST START ===");
      log.info("Request ID: {}", requestId);
      log.info("Method: {}", request.getMethod());
      log.info("URI: {}", request.getURI());
      log.info("Headers: {}", request.getHeaders());
      log.info("Remote Address: {}", request.getRemoteAddress());
      log.info("Timestamp: {}", LocalDateTime.now());

      return chain.filter(exchange.mutate().request(mutatedRequest).build())
          .then(Mono.fromRunnable(() -> {
            log.info("=== RESPONSE END ===");
            log.info("Request ID: {}", requestId);
            log.info("Status: {}", exchange.getResponse().getStatusCode());
            log.info("Timestamp: {}", LocalDateTime.now());
          }));
    };
  }

  public static class Config {
  }
}

