package com.product.productservice.dto;

import lombok.Data;

@Data
public class ProductResponse {
	private Long id;
	private Long shopId;
	private String name;
	private String description;
	private String category;
	private String brand;
	private String image;
	private Double price;
	private Integer stock;
}
