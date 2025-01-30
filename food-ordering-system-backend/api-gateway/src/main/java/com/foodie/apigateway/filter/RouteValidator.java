package com.foodie.apigateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class RouteValidator {

    public static final List<Pattern> openApiEndpoints = List.of(
            Pattern.compile("/auth/register"),
            Pattern.compile("/auth/login"),
            Pattern.compile("/food/products"),
            Pattern.compile("/food/products/search"),
            Pattern.compile("/food/product/\\d+/image"), // Dynamic pattern for productId
            Pattern.compile("/food/product/\\d+"),       // Dynamic pattern for id
            Pattern.compile("/eureka")
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(pattern -> pattern.matcher(request.getURI().getPath()).matches());

}