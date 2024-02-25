package com.product.productservice.rabbitMQ;

import com.product.productservice.model.Product;
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

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	public void reciveProductDetails(ProductDetails productDetails) {
		Product product = new Product();
		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setCategory(productDetails.getCategory());
		product.setBrand(productDetails.getBrand());
		product.setPrice(productDetails.getPrice());
		product.setStock(productDetails.getStock());
		product.setImage(productDetails.getImage());
		product.setShopId(productDetails.getShopId());

		productRepository.save(product);
	}
}
