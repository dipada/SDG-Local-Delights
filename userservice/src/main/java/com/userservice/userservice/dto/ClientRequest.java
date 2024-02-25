package com.userservice.userservice.dto;


import lombok.Data;

@Data
public class ClientRequest {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String shippingAddress;
	private String picture;
	private Boolean googleAccount;
}
