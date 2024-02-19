package com.delivery.deliveryservice.controller;

import com.delivery.deliveryservice.enumeration.DeliveryStatus;
import com.delivery.deliveryservice.model.Delivery;
import com.delivery.deliveryservice.rabbitMQ.RabbitMQSender;
import com.delivery.deliveryservice.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryRepository deliveryRepository;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    protected DeliveryController(DeliveryRepository deliveryRepository, RabbitMQSender rabbitMQSender) {
        this.deliveryRepository = deliveryRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping("/takeOrder")
    public ResponseEntity<String> takeOrder(@RequestParam Long orderId, @RequestParam Long shopId, @RequestParam Long userId) {

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setShopId(shopId);
        delivery.setUserId(userId);
        delivery.setStatus(DeliveryStatus.PICKED_UP);
        delivery.setUpdatedAt(Instant.now());

        deliveryRepository.save(delivery);

        rabbitMQSender.sendOrderTaken(delivery);
        return ResponseEntity.ok("Order" + delivery.getOrderId() + " taken");
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Delivery>> getAll() {
        return ResponseEntity.ok(deliveryRepository.findAll());
    }

    @PutMapping("/deliveryStatusInTransit")
    public ResponseEntity<String> updateDeliveryStatus(@RequestParam Long Id) {

        Delivery delivery = deliveryRepository.findById(Id).orElse(null);

        if (delivery == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        delivery.setStatus(DeliveryStatus.IN_TRANSIT);
        delivery.setUpdatedAt(Instant.now());

        deliveryRepository.save(delivery);

        return ResponseEntity.ok("Order" + delivery.getId() + " status updated to " + delivery.getStatus());

    }

    //set order status to delivered
    @PutMapping("/deliveryStatusDelivered")
    public ResponseEntity<String> updateDeliveryStatusDelivered(@RequestParam Long Id) {

        Delivery delivery = deliveryRepository.findById(Id).orElse(null);

        if (delivery == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setUpdatedAt(Instant.now());

        deliveryRepository.save(delivery);
        rabbitMQSender.sendOrderDelivered(delivery);

        return ResponseEntity.ok("Order" + delivery.getId() + " status updated to " + delivery.getStatus());

    }
}
