package com.foodie.orderservice.service;

import com.foodie.orderservice.model.Orders;
import com.foodie.orderservice.model.OrderStatus;
import com.foodie.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Orders createOrder(Orders orders) {
        orders.setCreatedAt(new Date());
        orders.setStatus(OrderStatus.PENDING);
        return orderRepository.save(orders);
    }

    public Orders updateOrderStatus(int orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId).get();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Orders getOrderById(int orderId) {
        return orderRepository.findById(orderId).get();
    }

    public Iterable<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
