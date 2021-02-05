package net.mguenther.gtd;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.method(HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH)
                        .uri("lb://gtd-es-command-side"))
                .route(r -> r.method(HttpMethod.GET)
                        .uri("lb://gtd-es-query-side"))
                .build();
    }
}