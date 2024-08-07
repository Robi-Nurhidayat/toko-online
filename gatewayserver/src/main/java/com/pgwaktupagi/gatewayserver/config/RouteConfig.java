package com.pgwaktupagi.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/pgwaktupagi/products/**")
                        .filters( f -> f.rewritePath("/pgwaktupagi/products/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("productsCircuitBreaker").setFallbackUri("forward:/contactSupport")))
                        .uri("lb://PRODUCTS"))
                .route(p -> p
                        .path("/pgwaktupagi/users/**")
                        .filters( f -> f.rewritePath("/pgwaktupagi/users/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path("/pgwaktupagi/carts/**")
                        .filters( f -> f.rewritePath("/pgwaktupagi/carts/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
                        .uri("lb://CARTS"))
                .route(p -> p
                        .path("/pgwaktupagi/orders/**")
                        .filters( f -> f.rewritePath("/pgwaktupagi/orders/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ORDERS")).build();


    }

}
