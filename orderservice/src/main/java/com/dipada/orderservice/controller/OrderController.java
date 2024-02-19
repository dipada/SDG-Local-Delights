package com.dipada.orderservice.controller;

import com.dipada.orderservice.dto.OrderRequest;
import com.dipada.orderservice.model.Order;
import com.dipada.orderservice.model.OrderStatus;
import com.dipada.orderservice.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    protected OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Operation(summary = "Get all orders of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders"),
            @ApiResponse(responseCode = "400", description = "Invalid user email supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        if (orderRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order request");
        }

        Order order = new Order();
        order.setUserEmail(orderRequest.getUserEmail());
        order.setShopId(orderRequest.getShopId());
        order.setListOfProductsIds(orderRequest.getListOfProductIds());
        order.setOrderStatus(OrderStatus.PENDING); //At creation, the order is pending
        order.setPaid(false);
        order.setAmount(orderRequest.getAmount());
        order.setTimestamp(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        order.setShippingAddress(orderRequest.getShippingAddress());

        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(order.getId().toString());
    }

    @Operation(summary = "Update the status of an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/update-order-status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus orderStatus) {
        if (orderId == null || orderStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order request");
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body("Order status updated successfully");
    }


    @Operation(summary = "Get all orders to be delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    @GetMapping("/get-to-be-delivered-orders")
    public ResponseEntity<List<Order>> getToBeDeliveredOrders() {
        List<Order> orders = orderRepository.findAllOrdersByOrderStatus(OrderStatus.TO_BE_DELIVERED).orElse(null);
        if (orders == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }


    @Operation(summary = "Take an order to deliver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order taken successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/take-order/{orderId}")
    public ResponseEntity<String> takeOrder(@PathVariable Long orderId, @RequestParam Long deliveryId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setDeliveryId(deliveryId);
        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body("Order taken successfully");
    }


    @Operation(summary = "Mark an order as delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/order-delivered/{orderId}")
    public ResponseEntity<String> orderDelivered(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body("Order delivered successfully");
    }

    @Operation(summary = "Get all orders of a delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    @GetMapping("/orderByDeliveryId/{deliveryId}")
    public ResponseEntity<List<Order>> getOrderByDeliveryId(@Parameter(description = "Delivery Id") @PathVariable Long deliveryId) {
        List<Order> orders = orderRepository.findAllOrdersByDeliveryId(deliveryId).orElse(null);
        if (orders == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

}
