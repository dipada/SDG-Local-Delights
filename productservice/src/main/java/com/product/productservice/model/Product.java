package com.product.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long shopId;
	private String name;
	private String description;
	private String category;
	private String brand;
	@Column(columnDefinition = "TEXT")
	private String image;
	private Double price;
	private Integer stock;
}
