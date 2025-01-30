package com.udara.paymentservice.controller;

import com.udara.paymentservice.dto.PaymentRequest;
import com.udara.paymentservice.dto.PaymentResponse;
import com.udara.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponse> checkout(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.checkout(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
