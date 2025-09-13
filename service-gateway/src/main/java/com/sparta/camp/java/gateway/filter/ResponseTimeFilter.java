package com.sparta.camp.java.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ResponseTimeFilter extends AbstractGatewayFilterFactory<ResponseTimeFilter.Config> {

  public ResponseTimeFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      long startTime = System.currentTimeMillis();
      ServerHttpRequest request = exchange.getRequest();

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("Response time for {} {}: {}ms",
            request.getMethod(),
            request.getURI(),
            duration);

        exchange.getResponse().getHeaders().add("X-Response-Time", duration + "ms");

        if (duration > config.getSlowRequestThreshold()) {
          log.warn("Slow request detected: {} {} took {}ms",
              request.getMethod(),
              request.getURI(),
              duration);
        }
      }));
    };
  }

  public static class Config {
    private long slowRequestThreshold = 1000; // milliseconds

    public long getSlowRequestThreshold() {
      return slowRequestThreshold;
    }

    public void setSlowRequestThreshold(long slowRequestThreshold) {
      this.slowRequestThreshold = slowRequestThreshold;
    }
  }
}

