package com.shop.shopservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shopservice.dto.ShopRequest;
import com.shop.shopservice.model.Shop;
import com.shop.shopservice.rabbitMQ.ProductDetails;
import com.shop.shopservice.rabbitMQ.RabbitMQSender;
import com.shop.shopservice.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopRepository shopRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;


    private ObjectMapper objectMapper;


    @Autowired
    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    //validazione token e estrazione email seller
    @PostMapping("/add")
    public ResponseEntity<String> addShop(@RequestBody ShopRequest shopRequest) {

        Shop shop = new Shop();
        shop.setName(shopRequest.getName());
        shop.setDescription(shopRequest.getDescription());
        shop.setAddress(shopRequest.getAddress());
        shop.setPhoneNumber(shopRequest.getPhoneNumber());
        shop.setEmail(shopRequest.getEmail());
        shop.setSellerEmail(shopRequest.getSellerEmail());

        shopRepository.save(shop);

        return ResponseEntity.status(200).body("Shop added successfully");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        return ResponseEntity.ok(shopRepository.findById(id).get());
    }

    //da fare con validazione token jwt da dove prendiamo email
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateShop(@PathVariable Long id, @RequestBody ShopRequest shopRequest) {
        Shop shop = shopRepository.findById(id).get();
        shop.setName(shopRequest.getName());
        shop.setDescription(shopRequest.getDescription());
        shop.setAddress(shopRequest.getAddress());
        shop.setPhoneNumber(shopRequest.getPhoneNumber());
        shop.setEmail(shopRequest.getEmail());
        shop.setSellerEmail(shopRequest.getSellerEmail());

        shopRepository.save(shop);

        return ResponseEntity.status(200).body("Shop updated successfully");
    }

    //da fare con validazione token jwt da dove prendiamo email
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
        return ResponseEntity.status(200).body("Shop deleted successfully");
    }

    //da fare con validazione token jwt da dove prendiamo email
    @GetMapping("/seller/{email}")
    public ResponseEntity<List<Shop>> getShopBySellerEmail(@PathVariable String email) {
        List<Shop> shops = shopRepository.findBySellerEmail(email);
        if (shops.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(shops);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Shop>> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        if (shops.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(shops);

    }

    // @PostMapping("/{shopId}" , value = "productdetails")
    @PostMapping("/addProduct/{shopId}")
    public String publishProductDetails(@RequestBody ProductDetails productDetails, @PathVariable Long shopId) {
        rabbitMQSender.sendAddProductRequest(productDetails, shopId);
        return "Product add request sent successfully";
    }



    /*
    @PostMapping("/addProduct/{shopId}")
    public ResponseEntity<String> addProduct(@PathVariable Long shopId, @RequestBody ProductDetails productDetails) throws JsonProcessingException {
        Optional<Shop> shop = shopRepository.findByIdAndSellerEmail(shopId, productDetails.getSellerEmail());
        if (shop.isPresent()) {
            rabbitMQSender.sendAddProductRequest(productDetails);
            return ResponseEntity.status(200).body("Product add request sent successfully");
        } else {
            return ResponseEntity.status(404).body("Shop not found for this seller");
        }
    }

     */



    /*
    @GetMapping("/testRabbit")
    public ResponseEntity<String> testRabbit() {

        rabbitMQSender.send("sono lo shop service");
        return ResponseEntity.status(200).body("Product add request sent successfully");
    }

     */


}
