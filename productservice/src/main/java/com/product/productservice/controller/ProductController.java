package com.product.productservice.controller;

import com.product.productservice.dto.ProductRequest;
import com.product.productservice.dto.ProductResponse;
import com.product.productservice.model.Product;
import com.product.productservice.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "Create a new product", description = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setShopId(productRequest.getShopId());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setBrand(productRequest.getBrand());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setShopId(productRequest.getShopId());

        productRepository.save(product);

        return ResponseEntity.status(200).body("Product added successfully");
    }

    @Operation(summary = "Get product by Id", description = "Get product by Id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.get().getId());
            productResponse.setShopId(product.get().getShopId());
            productResponse.setName(product.get().getName());
            productResponse.setDescription(product.get().getDescription());
            productResponse.setCategory(product.get().getCategory());
            productResponse.setBrand(product.get().getBrand());
            productResponse.setImage(product.get().getImage());
            productResponse.setPrice(product.get().getPrice());
            productResponse.setStock(product.get().getStock());

            return ResponseEntity.status(200).body(productResponse);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        if (products.iterator().hasNext()) {
            return ResponseEntity.status(200).body(products);
        }
        return ResponseEntity.status(404).body(null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(400).body("Product not found");
        }
        product.setName(productRequest.getName());
        product.setShopId(productRequest.getShopId());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setBrand(productRequest.getBrand());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        productRepository.save(product);

        return ResponseEntity.status(200).body("Product updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(404).body("Product not found");
        }
        productRepository.delete(product);
        return ResponseEntity.status(200).body("Product deleted successfully");
    }


}


