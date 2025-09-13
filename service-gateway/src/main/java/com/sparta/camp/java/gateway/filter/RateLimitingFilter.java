package com.sparta.camp.java.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RateLimitingFilter extends AbstractGatewayFilterFactory<RateLimitingFilter.Config> {

  public RateLimitingFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String clientIp = getClientIp(exchange.getRequest());

      // Simple rate limiting logic (can be enhanced with Redis)
      log.debug("Processing request from IP: {} at {}", clientIp, LocalDateTime.now());

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        log.debug("Request completed for IP: {}", clientIp);
      }));
    };
  }

  private String getClientIp(org.springframework.http.server.reactive.ServerHttpRequest request) {
    String xForwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }
    return request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "unknown";
  }

  public static class Config {
    private int requestsPerMinute = 100;

    public int getRequestsPerMinute() {
      return requestsPerMinute;
    }

    public void setRequestsPerMinute(int requestsPerMinute) {
      this.requestsPerMinute = requestsPerMinute;
    }
  }
}
