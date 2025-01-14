package com.foodie.orderservice.controller;


import com.foodie.orderservice.model.OrderStatus;
import com.foodie.orderservice.model.Orders;
import com.foodie.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable int orderId) {
        Orders order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Orders>> getAllOrders() {
        Iterable<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/add")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) {
        Orders createdOrders = orderService.createOrder(orders);
        return ResponseEntity.ok(createdOrders);
    }

    // update order status
    @PatchMapping("/{orderId}/set")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable int orderId, @RequestParam OrderStatus status) {
        Orders updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }


}
