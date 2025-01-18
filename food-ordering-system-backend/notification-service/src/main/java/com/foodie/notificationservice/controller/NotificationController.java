package com.foodie.notificationservice.controller;

import com.foodie.notificationservice.model.Notification;
import com.foodie.notificationservice.service.NotificationService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create-and-send")
    public ResponseEntity<String> createAndSendNotification(
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String recipient) {
        try {
            Notification notification = notificationService.createNotification(title, message, recipient);
            notificationService.sendNotification(notification);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (MessagingException | DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send notification.");
        }
    }
}