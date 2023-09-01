package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class CorsFilter extends AbstractGatewayFilterFactory<CorsFilter.Config> {

    public CorsFilter() {
        super(CorsFilter.Config.class);
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            System.out.println("CorsFilter 동작");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String origin = request.getHeaders().getOrigin();
            System.out.println(origin);
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                    "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization");

            return chain.filter(exchange);
        }, 0);
        return filter;

    }
}
