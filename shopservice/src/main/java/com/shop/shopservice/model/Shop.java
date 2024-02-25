package com.shop.shopservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_shops", uniqueConstraints = {
				@UniqueConstraint(columnNames = {"longitude", "latitude"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;
	private String address;
	private String phoneNumber;
	private String shopEmail;

	@Column(name = "longitude")
	private String longitude;
	@Column(name = "latitude")
	private String latitude;
	@Column(columnDefinition = "TEXT")
	private String imageUrl;

	@Column(nullable = false)
	private String sellerEmail;
}
