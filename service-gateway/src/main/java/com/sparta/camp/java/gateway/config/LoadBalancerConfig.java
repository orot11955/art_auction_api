package com.sparta.camp.java.gateway.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadBalancerConfig {

  @Bean
  @Primary
  public ServiceInstanceListSupplier serviceInstanceListSupplier() {
    return new ServiceInstanceListSupplier() {
      @Override
      public String getServiceId() {
        return "service-instances";
      }

      @Override
      public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays.asList(
        // new DefaultServiceInstance("service-auth-1", "service-auth", "service-auth",
        // 8081, false),
        // new DefaultServiceInstance("service-artwork-1", "service-artwork",
        // "service-artwork", 8082, false),
        // new DefaultServiceInstance("service-auction-1", "service-auction",
        // "service-auction", 8083, false),
        // new DefaultServiceInstance("service-bid-1", "service-bid", "service-bid",
        // 8084, false),
        // new DefaultServiceInstance("service-settlement-1", "service-settlement",
        // "service-settlement", 8085, false),
        // new DefaultServiceInstance("service-notification-1", "service-notification",
        // "service-notification", 8086,
        // false)
        ));
      }
    };
  }
}
