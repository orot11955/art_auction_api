package com.sparta.camp.java.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "auth-service"))
                        .uri("http://service-auth:8081"))

                .route("artwork-service", r -> r
                        .path("/api/artworks/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "artwork-service"))
                        .uri("http://service-artwork:8082"))

                .route("auction-service", r -> r
                        .path("/api/auctions/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "auction-service"))
                        .uri("http://service-auction:8083"))

                .route("bid-service", r -> r
                        .path("/api/bids/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "bid-service"))
                        .uri("http://service-bid:8084"))

                .route("settlement-service", r -> r
                        .path("/api/settlements/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "settlement-service"))
                        .uri("http://service-settlement:8085"))

                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Service-Name", "notification-service"))
                        .uri("http://service-notification:8086"))

                .build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            log.info("Global filter processing request: {} {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Global filter processing response: {}",
                        exchange.getResponse().getStatusCode());
            }));
        };
    }
}