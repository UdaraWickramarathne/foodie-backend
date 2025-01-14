package com.foodie.apigateway.filter;

import com.foodie.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }



    public static class Config {
        // Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(token != null && token.startsWith("Bearer")){
                    token = token.substring(7);
                }
                try {
                    jwtUtil.validateToken(token);
                } catch (Exception e){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

            }

            return chain.filter(exchange);
        };
    }
}
