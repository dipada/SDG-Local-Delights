package com.dipada.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String userEmail;
    private Long shopId;
    private List<Long> listOfProductIds;
    private Double amount;
}
