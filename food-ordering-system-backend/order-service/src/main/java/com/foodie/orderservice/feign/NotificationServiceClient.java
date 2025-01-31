package com.foodie.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationServiceClient {
    @PostMapping("/notifications/send")
    public ResponseEntity<String> createAndSendNotification(
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String recipient);
}
