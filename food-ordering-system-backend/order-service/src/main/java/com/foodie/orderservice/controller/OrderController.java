package com.foodie.orderservice.controller;


import com.foodie.orderservice.model.Orders;
import com.foodie.orderservice.model.OrderStatus;
import com.foodie.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable int orderId) {
        Orders orders = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable int userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Orders>> getAllOrders() {
        Iterable<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping()
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) {
        Orders createdOrders = orderService.createOrder(orders);
        return ResponseEntity.ok(createdOrders);
    }

    // update order status
    @PatchMapping("/{orderId}/set")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable int orderId, @RequestParam OrderStatus status) {
        Orders updatedOrders = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Orders>> getPendingOrders() {
        List<Orders> orders = orderService.getPendingOrders();
        return ResponseEntity.ok(orders);
    }
}
