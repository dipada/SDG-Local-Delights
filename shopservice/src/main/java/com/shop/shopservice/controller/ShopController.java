package com.shop.shopservice.controller;

import com.shop.shopservice.dto.ShopRequest;
import com.shop.shopservice.model.Shop;
import com.shop.shopservice.rabbitMQ.ProductDetails;
import com.shop.shopservice.rabbitMQ.RabbitMQSender;
import com.shop.shopservice.repository.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopRepository shopRepository;

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    protected ShopController(ShopRepository shopRepository, RabbitMQSender rabbitMQSender) {
        this.shopRepository = shopRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Operation(summary = "Add a new shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop added successfully"),})
    @PostMapping("/add")
    public ResponseEntity<String> addShop(@RequestBody ShopRequest shopRequest) {

        Shop shop = new Shop();
        modifyShopParameters(shopRequest, shop);

        shopRepository.save(shop);

        return ResponseEntity.status(HttpStatus.OK).body("Shop added successfully");
    }

    private static void modifyShopParameters(ShopRequest shopRequest, Shop shop) {
        shop.setName(shopRequest.getName());
        shop.setDescription(shopRequest.getDescription());
        shop.setAddress(shopRequest.getAddress());
        shop.setPhoneNumber(shopRequest.getPhoneNumber());
        shop.setEmail(shopRequest.getEmail());
        shop.setSellerEmail(shopRequest.getSellerEmail());
        shop.setLatitude(shopRequest.getLatitude());
        shop.setLongitude(shopRequest.getLongitude());
        shop.setImageUrl(shopRequest.getImageUrl());
    }


    @Operation(summary = "Get a shop by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop found"), @ApiResponse(responseCode = "404", description = "Shop not found")})
    @GetMapping("/get/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        Optional<Shop> shop = shopRepository.findById(id);
        return shop.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


    @Operation(summary = "Update a shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop updated successfully"),})
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateShop(@PathVariable Long id, @RequestBody ShopRequest shopRequest) {
        Optional<Shop> shop = shopRepository.findById(id);
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found");
        }


        modifyShopParameters(shopRequest, shop.get());

        shopRepository.save(shop.get());

        return ResponseEntity.status(HttpStatus.OK).body("Shop updated successfully");
    }

    @Operation(summary = "Delete a shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop deleted successfully"),})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Shop deleted successfully");
    }

    @Operation(summary = "Get a shop by seller email")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shop found"), @ApiResponse(responseCode = "404", description = "Shop not found")})
    @GetMapping("/seller/{email}")
    public ResponseEntity<List<Shop>> getShopBySellerEmail(@PathVariable String email) {
        List<Shop> shops = shopRepository.findBySellerEmail(email);
        if (shops.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shops);
    }


    @Operation(summary = "Get all shops")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Shops found"), @ApiResponse(responseCode = "404", description = "Shops not found")})
    @GetMapping("/all")
    public ResponseEntity<List<Shop>> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        if (shops.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shops);

    }

    @Operation(summary = "Add a new product to a shop")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Product add request sent successfully"),})
    @PostMapping("/addProduct/{shopId}")
    public String publishProductDetails(@RequestBody ProductDetails productDetails, @PathVariable Long shopId) {
        rabbitMQSender.sendAddProductRequest(productDetails, shopId);
        //TODO gestire risposta da consumer e dopo dare risposta al client
        return "Product add request sent successfully";
    }

}
