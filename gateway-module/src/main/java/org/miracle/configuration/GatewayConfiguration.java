package org.miracle.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;


/**
 * Класс конфигурации Gateway
 * @author Mark Valiev
 * @since 08.09.2024
 */
@Configuration
@EnableConfigurationProperties(GatewayConfiguration.class)
@ConfigurationProperties(prefix = "gateway")
@Getter
@Setter
public class GatewayConfiguration {

    private List<GatewayRoute> routes;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        RouteLocatorBuilder.Builder customBuilder = builder.routes();

        for (var route: routes) {
            customBuilder = customBuilder
                    .route(r -> r.path(route.getContextPath())
                            .and()
                            .uri(route.getUri()));
        }
        return customBuilder.build();
    }
}

