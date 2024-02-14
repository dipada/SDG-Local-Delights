package com.delivery.deliveryservice.controller;

import com.delivery.deliveryservice.enumeration.DeliveryStatus;
import com.delivery.deliveryservice.model.Delivery;
import com.delivery.deliveryservice.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    protected DeliveryController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    //todo inserire endpoint per presa in carico ordine
    @PostMapping("/takeOrder")
    public ResponseEntity<String> takeOrder(@RequestParam Long orderId, @RequestParam Long shopId, @RequestParam Long userId) {

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setShopId(shopId);
        delivery.setUserId(userId);
        delivery.setStatus(DeliveryStatus.PICKED_UP);
        delivery.setUpdatedAt(Instant.now());

        deliveryRepository.save(delivery);

        return ResponseEntity.ok("Order" + delivery.getOrderId() + " taken");


    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Delivery>> getAll() {
        return ResponseEntity.ok(deliveryRepository.findAll());
    }

    //set order status to in transit
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

        return ResponseEntity.ok("Order" + delivery.getId() + " status updated to " + delivery.getStatus());

    }
 



    //per tutte le modifiche Controllare che la richiesta provenga dal deliverer che ha preso in carico l'ordine.
    //todo inserire endpoint per consegna ordine
    //todo inserire endpoint per annullamento ordine
    //todo inserire endpoint per visualizzare ordini presi in carico
    //todo inserire endpoint per visualizzare ordini consegnati
    //todo inserire endpoint per visualizzare ordini annullati
    //todo inserire endpoint per visualizzare ordini in attesa di presa in carico
    //todo inserire endpoint per visualizzare ordini in attesa di consegna
    //todo inserire endpoint per visualizzare ordini in attesa di annullamento
    //todo inserire endpoint per visualizzare ordini in attesa di presa in carico di un determinato utente
    //todo inserire endpoint per visualizzare ordini in attesa di consegna di un determinato utente

}
