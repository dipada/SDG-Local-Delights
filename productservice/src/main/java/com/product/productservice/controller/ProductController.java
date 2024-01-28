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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, RabbitTemplate rabbitTemplate) {
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
        modifyProductParameters(productRequest, product);

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body("Product added successfully");
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

            return ResponseEntity.status(HttpStatus.OK).body(productResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(summary = "Get all products", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the products"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @GetMapping("/all")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        if (products.iterator().hasNext()) {
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(summary = "Update a product", description = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        modifyProductParameters(productRequest, product);

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully");
    }

    @Operation(summary = "Delete a product", description = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

    private static void modifyProductParameters(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setShopId(productRequest.getShopId());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setBrand(productRequest.getBrand());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setShopId(productRequest.getShopId());
    }



}


