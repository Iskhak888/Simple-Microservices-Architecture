package org.example.gatewayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Kuttubek Aidaraliev
 *
 */

@Configuration
public class SpringCloudConfig {

    private static final String SEGMENT = "/${segment}";

    @Value("${auth.server.url}")
    private String authServerURL;

    @Value("${main.server.url}")
    private String mainServerURL;

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r
                        .path("/authenticate", "/sign")
                        .uri(authServerURL))
                .route("main", r -> r
                        .path("/main/**")
                        .uri(mainServerURL))
                .build();
    }
}
