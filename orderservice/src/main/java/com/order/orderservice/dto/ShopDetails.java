package com.order.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetails {
    private Long id;
    private String name;
    private String address;
    private String email;
}
