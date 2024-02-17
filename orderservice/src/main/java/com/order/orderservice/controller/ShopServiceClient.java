package com.order.orderservice.controller;

import com.order.orderservice.dto.ShopDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopServiceClient {
    private final RestTemplate restTemplate;
    private final String shopServiceUrl;

    public ShopServiceClient(RestTemplate restTemplate, @Value("${shopservice.url}") String shopServiceUrl) {
        this.restTemplate = restTemplate;
        this.shopServiceUrl = shopServiceUrl;
    }

    public ShopDetails getShopDetails(Long shopId) {
        ResponseEntity<ShopDetails> response = restTemplate.getForEntity(
                shopServiceUrl + "/shop/details/" + shopId,
                ShopDetails.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }else {
            throw new RuntimeException("Unable to retrieve shop details for shopId: " + shopId);
        }
    }
}
