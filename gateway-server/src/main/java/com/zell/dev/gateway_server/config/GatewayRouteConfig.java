package com.zell.dev.gateway_server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("redirect-api", r -> r.path("/api/r/**")
                        .uri("http://redirect-api:8082"))
                .route("shorten-api", r -> r.path("/api/shorten/**")
                        .uri("http://shorten-api:8081"))
                .build();
    }
}
