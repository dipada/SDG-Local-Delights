package com.dipada.orderservice.dto;

import com.dipada.orderservice.model.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
	private Long id;
	private String userEmail;
	private Long shopId;
	private List<Long> listOfProductIds;
	private Boolean paid;
	private OrderStatus orderStatus;
}
