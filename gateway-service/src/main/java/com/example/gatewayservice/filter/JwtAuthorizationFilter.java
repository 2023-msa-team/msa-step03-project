package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gatewayservice.utils.ApiUtils;
import com.example.gatewayservice.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    private Environment env;
    private ObjectMapper om;

    public JwtAuthorizationFilter(Environment env, ObjectMapper om){
        super(JwtAuthorizationFilter.Config.class);
        this.env = env;
        this.om = om;
    }

    @Override
    public GatewayFilter apply(JwtAuthorizationFilter.Config config) {
        
        return (exchange, chain) -> {
            System.out.println("JwtAuthorizationFilter 동작");
            // reactive 타입 (flux, mono)
            ServerHttpRequest request = exchange.getRequest();

            // Authorization 헤더 존재 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "토큰이 없습니다", HttpStatus.UNAUTHORIZED);
            }

            // 토큰 검증
            String jwt = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            try {
                DecodedJWT decodedJWT = JwtTokenUtils.verify(jwt, env);
                String id = decodedJWT.getSubject();
                if(id == null || id.isBlank()){
                    return onError(exchange, "토큰 페이로드 검증 실패", HttpStatus.UNAUTHORIZED);
                }

                // 마이크로서비스로 헤더 정보 넘기는 법
                // mutate() 메서드는 ServerHttpRequest의 불변성(Immutability)을 유지하면서도 수정된 새로운 ServerHttpRequest 객체를 생성합니다. 
                // 이를 통해 원래의 요청 객체를 변경하지 않고 새로운 속성을 추가하거나 수정할 수 있습니다.
                request.mutate().header("X-Authorization-Id", id);

                System.out.println("exchage에 값 담김 : "+id);
            } catch (SignatureVerificationException | JWTDecodeException ve) {
                return onError(exchange, "토큰 검증 실패", HttpStatus.UNAUTHORIZED);
            } catch (TokenExpiredException tee) {
                return onError(exchange, "토큰 만료", HttpStatus.UNAUTHORIZED);
            }

            // 다음 필터로 이동
            return chain.filter(exchange);
        }; 
    }


    public static class Config {

    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiUtils.ApiResult<?> apiUtils = ApiUtils.error(err, httpStatus);
        try {
            String responseBody = om.writeValueAsString(apiUtils);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
        } catch (Exception e) {
            return response.writeWith(Mono.just(response.bufferFactory().wrap("MessageConverter Error".getBytes())));
        }
    }
}
