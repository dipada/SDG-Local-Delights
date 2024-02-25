package com.userservice.userservice.dto;

import lombok.Data;

@Data
public class SellerResponse {

	private String email;
	private String firstName;
	private String lastName;
	private String picture;
	private String phoneNumber;
	private String vatNumber;
	private Boolean googleAccount;

}
