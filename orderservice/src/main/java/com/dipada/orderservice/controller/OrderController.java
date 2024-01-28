package com.dipada.orderservice.controller;

import com.dipada.orderservice.model.Order;
import com.dipada.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    protected OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/get-all-orders/{userEmail}")
    public ResponseEntity<List<Order>> getAllUserOrders(@PathVariable String userEmail) {
        if (userEmail == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Order> orders = orderRepository.findAllOrdersByUserEmail(userEmail).orElse(null);
        if (orders == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
