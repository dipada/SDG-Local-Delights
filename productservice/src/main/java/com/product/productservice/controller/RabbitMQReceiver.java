package com.product.productservice.controller;

import com.product.productservice.rabbitMQ.ProductDetails;
import com.product.productservice.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {

    @Autowired
    ProductRepository productRepository;

    /*
    @RabbitListener(queues = "${queue.name}")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }

     */


    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void reciveProductDetails(ProductDetails productDetails) {
        System.out.println("Received Product Details " + productDetails);
    }



    /*
    @RabbitListener(queues = "${queue.name}")
    public void addProductFromShop(ProductDetails productDetails, Long shopId) {
        Product product = new Product();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setImage(productDetails.getImage());

        productRepository.save(product);
        System.out.println("Received < add product >");
    }

     */


}
