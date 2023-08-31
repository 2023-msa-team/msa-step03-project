package com.example.gatewayservice.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// application.yml 마다 등록을 안해도 글로벌로 설정된다.
@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() { 
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(LoggingFilter.Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("Logging Filter Start : request id -> {}", request.getId() );
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()){
                    log.info("Logging Filter End : response code -> {}", response.getStatusCode() );
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE);
        return filter;
        /*
        Spring Framework에서는 컨테이너에서 빈(Bean)을 등록할 때 빈의 우선순위를 지정할 수 있습니다.
        이때 "Ordered.HIGHEST_PRECEDENCE"는 가장 높은 우선순위를 나타내는 상수입니다.
        즉, 이 상수를 사용하여 등록된 빈은 다른 빈보다 먼저 처리됩니다.
         */
    }

    @Getter @Setter
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
