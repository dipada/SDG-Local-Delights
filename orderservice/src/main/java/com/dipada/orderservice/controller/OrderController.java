package com.dipada.orderservice.controller;

import com.dipada.orderservice.repository.OrderRepository;
import com.dipada.orderservice.RabbitMQ.RabbitMQSender;
import com.dipada.orderservice.dto.OrderRequest;
import com.dipada.orderservice.dto.ShopDetails;
import com.dipada.orderservice.model.Order;
import com.dipada.orderservice.model.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ShopServiceClient shopServiceClient;

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    protected OrderController(OrderRepository orderRepository, ShopServiceClient shopServiceClient, RabbitMQSender rabbitMQSender) {
        this.orderRepository = orderRepository;
        this.shopServiceClient = shopServiceClient;
        this.rabbitMQSender = rabbitMQSender;
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

    //TODO inserisci ordine
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

        //TODO check if products exist, or check in frontend

        //recupero dettagli negozio dell'ordine
        ShopDetails shopDetails = shopServiceClient.getShopDetails(orderRequest.getShopId());

        System.out.println("ShopDetails recived from OrderService: " + shopDetails);


        Order order = new Order();
        order.setUserEmail(orderRequest.getUserEmail());
        order.setShopId(orderRequest.getShopId());
        order.setShopName(shopDetails.getName());
        order.setShopAddress(shopDetails.getAddress());
        order.setShopEmail(shopDetails.getEmail());
        order.setListOfProductsIds(orderRequest.getListOfProductIds());
        order.setOrderStatus(OrderStatus.PENDING); //At creation, the order is pending
        order.setPaid(false);
        order.setAmount(orderRequest.getAmount());
        order.setTimestamp(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        orderRepository.save(order);

        rabbitMQSender.sendOrder(order);

        return ResponseEntity.status(HttpStatus.OK).body("Order created successfully");
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
}
