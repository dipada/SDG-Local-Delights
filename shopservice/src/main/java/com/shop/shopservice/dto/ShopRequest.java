package com.shop.shopservice.dto;

import lombok.Data;

@Data
public class ShopRequest {

	private String name;
	private String description;
	private String address;
	private String phoneNumber;
	private String shopEmail;
	private String sellerEmail;
	private String longitude;
	private String latitude;
	private String imageUrl;
}
