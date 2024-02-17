package com.delivery.deliveryservice.dto;

import lombok.Data;

@Data
public class DeliveryRequest {

        private Long orderId;
        private Long shopId;
        private Long userId;
        private String status;
}
