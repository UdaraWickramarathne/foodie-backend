package com.foodie.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE")
public interface UserServiceClient {

    @GetMapping("/auth/email/{userId}")
    public ResponseEntity<?> getEmail(@PathVariable String userId);
}
